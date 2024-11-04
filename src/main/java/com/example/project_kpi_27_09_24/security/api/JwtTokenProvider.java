package com.example.project_kpi_27_09_24.security.api;




import com.example.project_kpi_27_09_24.entity.auth.RefreshToken;
import com.example.project_kpi_27_09_24.entity.auth.User;
import com.example.project_kpi_27_09_24.exception.RefreshTokenException;
import com.example.project_kpi_27_09_24.repository.RefreshTokenRepository;
import com.example.project_kpi_27_09_24.repository.UserRepository;
import com.example.project_kpi_27_09_24.security.AuthService;
import com.example.project_kpi_27_09_24.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String secret;

    @Value("${app.jwtExpirationInMs}")
    private Long expiredTime;

    @Value("${app.jwt.refresh.tokentokenExpirationInMs}")
    private Long expireTimeRefreshToken;


    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public JwtTokenProvider() {
    }

    public JwtTokenProvider(AuthService authService) {
        this.authService = authService;
    }

    public JwtTokenProvider(RefreshTokenRepository refreshTokenRepository, UserService userService, AuthService authService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
        this.authService = authService;
    }

    public String generateToken(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

       /// System.out.println(authentication.getPrincipal().toString()); ;
        Date  exptime = new Date(new Date().getTime() + expiredTime);
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .setIssuer(user.getUsername())
                .setExpiration(exptime)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public boolean validateToken(String token) {

        try {
          //  System.out.println(token.toString());
            Claims claims = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
          //  System.out.println(claims.getSubject());
            return true;
        } catch (SignatureException e) {
            log.error("Invalid  JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid  JWT  token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;

    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }



    public String generateTokenByUser(User user) {

        Date exptime = new Date(new Date().getTime() + expiredTime);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .setIssuer(user.getUsername())
                .setExpiration(exptime)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    /// RefreshToken  config  methods  ///


    public RefreshToken createRefreshToken(Long userId)  {
             ///Bu joyda tekshirish davomida xatolik chiqganligi uchun qaytarildi...
             ///Bu joyda bazadan olganda idis bilan keladi jpada update= getbyId()+Save()

         User user=userService.getOptionalById(userId).orElseThrow(()->new EntityNotFoundException("topilmadi"+userId));
        Optional<RefreshToken> rt = refreshTokenRepository.findByUser(user);
        System.out.println(rt.toString());
        if (rt.isPresent()) {
            RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElseThrow(()->new EntityNotFoundException("topilmadi"));
         //   System.out.println("refresh token== " + refreshToken);
             refreshToken.setRefreshToken(UUID.randomUUID().toString());
             refreshToken.setUser(user);
            refreshToken.setExpiryDate(Instant.now().plusMillis(expireTimeRefreshToken));
            //System.out.println("refresh token yangilandi == " + refreshToken);
            return refreshTokenRepository.save(refreshToken);
        }
        System.out.println("Bu user id=" + userId);
        RefreshToken refreshToken = new RefreshToken();
          refreshToken.setRefreshToken(UUID.randomUUID().toString());
          refreshToken.setExpiryDate(Instant.now().plusMillis(expireTimeRefreshToken));
          refreshToken.setUser(user);
         RefreshToken refreshTokenS = refreshTokenRepository.save(refreshToken);
          // System.out.println("refresh token");
          // System.out.println(refreshToken.toString());
               return   refreshTokenS;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate() == null) {
            throw new RefreshTokenException(token.getRefreshToken(), "Xatolik sodir bo'ldi..!!>>>");
        }  else if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(token.getRefreshToken(), " Xatolik sodir bo'ldi..!!>");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token) throws SQLException {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    @Transactional
    public int  deleteByUser(Long userId)  {
        return  refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }


}



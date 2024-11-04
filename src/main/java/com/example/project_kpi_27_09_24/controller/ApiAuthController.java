package com.example.project_kpi_27_09_24.controller;


import com.example.project_kpi_27_09_24.dto.api.ApiResponse;
import com.example.project_kpi_27_09_24.dto.api.JwtResponse;
import com.example.project_kpi_27_09_24.dto.api.LoginRequest;
import com.example.project_kpi_27_09_24.dto.api.RefreshTokenRequest;
import com.example.project_kpi_27_09_24.entity.auth.RefreshToken;
import com.example.project_kpi_27_09_24.entity.auth.User;
import com.example.project_kpi_27_09_24.security.AuthService;
import com.example.project_kpi_27_09_24.security.api.JwtTokenProvider;
import com.example.project_kpi_27_09_24.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/api/auth"})
public class ApiAuthController {
    @Autowired
    private final AuthService    authService;
    @Autowired
    private final JwtTokenProvider  jwtTokenProvider;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserService  userService;


    public ApiAuthController(AuthService authService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserService userService) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }



    @PostMapping(value = {"/login"})
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.toString());


        if (!authService.checkUserByUsername(loginRequest.getUserName())) {
            System.out.println(authService.checkUserByUsername(loginRequest.getUserName()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Bunday Username topilmadi..!!"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.CHECKPOINT).body(" Foydalanuvchi  logini  mavjud  emas ");
        }


        String   token=jwtTokenProvider.generateToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user=(User) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse();
        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        jwtResponse.setRefreshToken(refreshToken.getRefreshToken());
        jwtResponse.setUserName(user.getUsername());
        jwtResponse.setTokenBody(jwtTokenProvider.generateToken(authentication));
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);

    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) throws SQLException {
        //System.out.println(request);
        String requestRefreshToken = request.getRefreshToken();
        Optional<RefreshToken> refreshTokenRequest = jwtTokenProvider.findByToken(requestRefreshToken);
        //System.out.println(refreshTokenRequest);
        if (!refreshTokenRequest.isPresent()) {
            return ResponseEntity.status(404).body(new ApiResponse(false, "Refresh token bazada mavjud emas"));
        }
        User user = refreshTokenRequest.get().getUser();
        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        String token = jwtTokenProvider.generateTokenByUser(user);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setUserName(user.getUsername());
        jwtResponse.setRefreshToken(refreshToken.getRefreshToken());
        jwtResponse.setTokenBody(token);
        return ResponseEntity.ok(jwtResponse);

    }


}

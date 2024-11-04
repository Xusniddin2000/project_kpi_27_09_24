package com.example.project_kpi_27_09_24.security.api;



import com.example.project_kpi_27_09_24.security.AuthService;
import com.example.project_kpi_27_09_24.utils.AppConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthService authService;



    @Value("${app.tokenType}")
    private String tokenType;

    @Override
    protected void doFilterInternal( @NonNull  HttpServletRequest httpServletRequest,
                                     @NonNull HttpServletResponse httpServletResponse,
                                     @NonNull FilterChain filterChain  )  throws   ServletException, IOException {

        String url = httpServletRequest.getRequestURI();

        System.out.println(url.toString());
        System.out.println(httpServletResponse.getStatus());

        if (!AppConstants.openUrl.contains(url) && url.startsWith("/api/")) {
            String jwt = getJwtTypeFromRequest(httpServletRequest);
            if (jwt == null) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401, Jwt token mavjud emas!!!");
                return;
            }

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                try {
                    String userId = tokenProvider.getUserIdFromToken(jwt);
                    //  System.out.println(userId);
                    if (!authService.checkUser(Long.valueOf(userId))) {
                        httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bazada ushbu ushbu user mavjud emas");
                        return;
                    }
                    UserDetails userDetails = authService.loadUserById(Long.valueOf(userId));
                    //bu loglarni yozish uchun
                    //saveLog(httpServletRequest, userDetails);
                    //gibrit app uchun shu if quyilishi kerak
                    if (authService.isAuthenticate()) {
                        UsernamePasswordAuthenticationToken authentication = new
                                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                    ;
                } catch (Exception e) {
                    logger.error("Could not set user authentication in security context", e);
                    return;
                }

            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401  ruxsat berilmagan xatolik.");
                return;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    public String  getJwtTypeFromRequest(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.substring(0, tokenType.length()).equals(tokenType)) {
            return token.substring(tokenType.length() + 1);
        }
        return "Xatolik bor Authentication Filterda";
    }


}

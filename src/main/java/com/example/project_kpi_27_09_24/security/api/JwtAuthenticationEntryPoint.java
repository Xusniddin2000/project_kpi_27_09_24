package com.example.project_kpi_27_09_24.security.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);



    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        System.out.println(request.toString());
        System.out.println(e.getMessage());
        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
        response.addHeader("WWW-Authenticate", "Basic realm=" + e.getLocalizedMessage() + "");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + e.getMessage()+"\n"+request.getRequestURL()+" method " +request.getMethod());
    }
}

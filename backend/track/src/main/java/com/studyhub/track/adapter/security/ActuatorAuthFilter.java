package com.studyhub.track.adapter.security;

import com.studyhub.track.application.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ActuatorAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    public ActuatorAuthFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("ActuatorAuth");
        String token = jwtService.extractTokenFromHeader(header);

        if (token == null || !token.equals("abcdefg")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden: Missing or invalid token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

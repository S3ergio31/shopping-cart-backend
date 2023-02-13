package com.shoppingcart.backend.security;

import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private Jwt jwt;

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwt = new Jwt();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        getUserFromToken(request),
                        null,
                        new ArrayList<>()
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private String getUserFromToken(HttpServletRequest request){
        String token =request.getHeader("Authorization");
        String bearerPrefix = "Bearer ";

        if(token == null || token.startsWith(bearerPrefix)){
            throw new JwtException("Token not found");
        }

        token = token.replace(bearerPrefix, "");
        String payload = jwt.decode(token).getSubject();

        if(payload != null) {
            return payload;
        }
        throw new JwtException("Token could not be decoded");
    }
}

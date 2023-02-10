package com.shoppingcart.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcart.backend.AppContext;
import com.shoppingcart.backend.requests.LoginRequest;
import com.shoppingcart.backend.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private Jwt jwt;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwt = new Jwt();
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException {
        LoginRequest loginRequest = createLoginRequest(request);
        return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword(),
                new ArrayList<>()
            )
        );
    }

    private LoginRequest createLoginRequest(HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(
                request.getInputStream(),
                LoginRequest.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult
    ) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserService userService = (UserService) AppContext.getBean(UserService.class);
        com.shoppingcart.backend.domain.User user = userService.findByEmail(username);
        String token = jwt.encode(username);
        response.addHeader("Authorization", token);
        response.addHeader("User-ID", user.getId().toString());
    }
}

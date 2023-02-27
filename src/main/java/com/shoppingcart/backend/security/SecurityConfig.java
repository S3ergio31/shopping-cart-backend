package com.shoppingcart.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
            .cors()
            .and()
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**")
                .permitAll()

                .antMatchers(HttpMethod.GET, "/products/**", "/categories")
                .permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(getAuthenticationFilter())
            .addFilter(getAuthorizationFilter());
        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter filter = new AuthenticationFilter(
            authenticationManager()
        );
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

    private AuthorizationFilter getAuthorizationFilter() throws Exception{
        AuthorizationFilter filter = new AuthorizationFilter(authenticationManager());
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

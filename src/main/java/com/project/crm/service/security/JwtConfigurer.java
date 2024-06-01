package com.project.crm.service.security;

import com.project.crm.service.impl.UserDetailsServiceImpl;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtUtils jwtUtils;

    private UserDetailsServiceImpl userDetailsService;


    public JwtConfigurer(JwtUtils jwtUtils ,  UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;

    }

   /* @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthTokenFilter customFilter = new AuthTokenFilter(jwtUtils , userDetailsService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }*/
}

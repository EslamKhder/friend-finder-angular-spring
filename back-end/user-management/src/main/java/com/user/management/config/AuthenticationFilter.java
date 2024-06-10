package com.user.management.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        return checkPath(path, SecurityConfig.PUBLIC_APIS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        Optional<String> basicAuthorization =
                Optional.ofNullable(authorizationHeader).filter(header -> header.startsWith("Basic "));

        if (basicAuthorization.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> jwt =
                Optional.ofNullable(authorizationHeader).filter(header -> header.startsWith("Bearer "));



    }

    private Boolean checkPath(String path, String [] paths){

        // /user/create
        String[] parts = path.split("/");
        String newPath = "/" + parts[1] + "/**";

        return Arrays.asList(paths).contains(newPath);
    }
}

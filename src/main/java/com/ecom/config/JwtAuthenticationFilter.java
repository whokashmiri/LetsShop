package com.ecom.config;


import com.ecom.repository.auth.UserRepository;
import com.ecom.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService , UserRepository userRepository ){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

       String authHeader =  request.getHeader("Authorization");

       if (authHeader == null || !authHeader.startsWith("Bearer ")){
           filterChain.doFilter(request , response);
            return;
       }

        String token = authHeader.substring(7);
      String userId = jwtService.extractUserId(token);

        System.out.println("JWT Token: " + token);

        System.out.println("JWT UserId: " + userId);

       userRepository.findById(userId).ifPresent(user -> {
           System.out.println("User Found " + user.getEmail());
           UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(
                   user , null , user.getRoles().stream()
                   .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                   .toList()
           );
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);


               }


       );

        filterChain.doFilter(request, response);

    }
}

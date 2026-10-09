package com.yers4.gestion_tareas.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class JwtAuthentication extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override 

    protected  void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    )throws ServletException,IOException{

        //extraer el encabezado
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        //si no hay encabezado pasa sin continuar con el filtro
        if(authHeader == null || !authHeader .startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        //extraemos el token quitando el prefijo bearer
        jwt = authHeader.substring(7);
        userEmail = jwtService.extraerEmail(jwt);

        if (userEmail == null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.elTokenEsValido(jwt, userDetails.getUsername())) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null,
                    userDetails.getAuthorities());  

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                 SecurityContextHolder.getContext().setAuthentication(authToken);
            }          
        }

        filterChain.doFilter(request, response);


    }



}
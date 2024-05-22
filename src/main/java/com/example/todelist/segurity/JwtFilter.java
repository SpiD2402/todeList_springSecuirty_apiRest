package com.example.todelist.segurity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
@Component
@Slf4j
public class JwtFilter  extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final  JwtUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final var requestTokenHeader= request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer "))
        {
                token = requestTokenHeader.substring(7);
                try{
                    username = jwtService.getUsernameFromToken(token);
                }
                catch (IllegalArgumentException e)
                {
                    log.error(e.getMessage());

                }
        }

        if (Objects.nonNull(username) &&  Objects.isNull(SecurityContextHolder.getContext().getAuthentication()))
        {
            final var userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            if (userDetails.isEnabled() && this.jwtService.validateToken(token,userDetails))
            {
                var usernameAndPasswordAuthToken= new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());
                usernameAndPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordAuthToken);
            }

        }
        filterChain.doFilter(request,response);


    }
}

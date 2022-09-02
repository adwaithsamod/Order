package com.example.order.security;


import com.example.order.entities.JwtToken;
import com.example.order.services.JwtTokenService;
import com.example.order.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UsersService usersService;

    @Autowired
    private  JwtUtil jwtUtil;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        final String authorizationHeader= httpServletRequest.getHeader("Authorization");
        String username=null;
        String jwt=null;

        if(authorizationHeader!=null&&authorizationHeader.startsWith("Bearer "))
        {
            jwt=authorizationHeader.substring(7);
            username=jwtUtil.extractUsername(jwt);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {

            UserDetails userDetails=this.usersService.loadUserByUsername(username);
            JwtToken jwtToken = jwtTokenService.findByUsername(username);
            if(jwtToken == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please Login" );
            if(jwtUtil.validateToken(jwt,userDetails))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=jwtUtil.getAuthenticationToken(jwt,SecurityContextHolder.getContext().getAuthentication(), userDetails);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                logger.info("Authenticated User "+ username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
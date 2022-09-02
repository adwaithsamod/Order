package com.example.order.services;

import com.example.order.entities.JwtToken;
import com.example.order.repositories.JwtTokenRepository;
import com.example.order.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtTokenService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private JwtTokenRepository jwtTokenRepository;
    public JwtToken findByUsername(String username) {
       return jwtTokenRepository.findByUsername(username);
    }

    public void addToken(JwtToken token) {
        jwtTokenRepository.save(token);
    }

    public boolean ifTokenAlreadyExist(String username) {
        JwtToken jwtToken = jwtTokenRepository.findByUsername(username);
        if(jwtToken==null)
            return false;
        else
            return true;
    }

    public void deleteToken(String username) {
        JwtToken jwtToken = jwtTokenRepository.findByUsername(username);
        jwtTokenRepository.delete(jwtToken);
    }

    public void logout() {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            deleteToken(username);
        }
    }
}

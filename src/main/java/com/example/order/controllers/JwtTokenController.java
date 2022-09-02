package com.example.order.controllers;

import com.example.order.dto.AuthenticationRequest;
import com.example.order.dto.AuthenticationResponse;
import com.example.order.entities.JwtToken;
import com.example.order.security.JwtUtil;
import com.example.order.services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtTokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(method = RequestMethod.POST,value = "authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(jwtTokenService.ifTokenAlreadyExist(authenticationRequest.getUsername()))
            return ResponseEntity.ok("already logged in");

        final String token = jwtUtil.generateToken(authentication);
        JwtToken jwtToken = new JwtToken(authenticationRequest.getUsername(),token);
        jwtTokenService.addToken(jwtToken);
        return  ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "logout")
    public void logout(){
        jwtTokenService.logout();
    }
}

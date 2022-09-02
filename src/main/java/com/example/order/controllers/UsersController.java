package com.example.order.controllers;

import com.example.order.controllers.request.UserCreateRequest;
import com.example.order.dto.AuthenticationRequest;
import com.example.order.dto.AuthenticationResponse;
import com.example.order.entities.JwtToken;
import com.example.order.entities.Users;
import com.example.order.responseModel.Response;
import com.example.order.security.JwtUtil;
import com.example.order.services.JwtTokenService;
import com.example.order.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.POST,value = "users")
    public Response addUsers(@RequestBody UserCreateRequest userCreateRequest){
        return usersService.addUsers(userCreateRequest);
    }

    @RequestMapping("/users")
    public Response getUsers(){
        return new Response(true,"Registered Users", usersService.getAllUsers()) ;
    }

}
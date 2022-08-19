package com.example.order.controllers;

import com.example.order.entities.Authorities;
import com.example.order.responseModel.Response;
import com.example.order.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping(method = RequestMethod.POST,value = "authorities")
    public Response addAuthorities(@RequestBody List<Authorities> authorities){
        return authorityService.addAuthorities(authorities);
    }
}

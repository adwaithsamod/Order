package com.example.order.controllers;

import com.example.order.controllers.request.UserCreateRequest;
import com.example.order.entities.Users;
import com.example.order.responseModel.Response;
import com.example.order.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.POST,value = "users")
    public Response addUsers(@RequestBody UserCreateRequest userCreateRequest){
        return usersService.addUsers(userCreateRequest);
    }
}


package com.example.order.controllers;

import com.example.order.entities.Role;
import com.example.order.responseModel.Response;
import com.example.order.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.POST,value = "roles")
    public Response addRoles(@RequestBody List<Role> roles){
        return roleService.addRoles(roles);
    }
}
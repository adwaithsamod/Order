package com.example.order.services;

import com.example.order.entities.Role;
import com.example.order.repositories.RoleRepository;
import com.example.order.responseModel.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    public Response addRoles(List<Role> roles) {
        roleRepository.saveAll(roles);
        return new Response(true,"Added roles",roles);

    }

    public Role findByName(String username) {
        return roleRepository.findByName(username);
    }
}
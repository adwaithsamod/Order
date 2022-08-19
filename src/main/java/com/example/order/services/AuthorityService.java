package com.example.order.services;

import com.example.order.entities.Authorities;
import com.example.order.repositories.AuthorityRepository;
import com.example.order.responseModel.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;
    public Response addAuthorities(List<Authorities> authorities) {
        authorityRepository.saveAll(authorities);
        return new Response(true,"Added authorities",authorities);

    }
}

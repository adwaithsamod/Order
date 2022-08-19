package com.example.order.services;

import com.example.order.entities.Gender;
import com.example.order.entities.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return (UserDetails) new Users("ashik","8989898989","adwaithsasmod@gmail.com",9, Gender.male,"India","ashik","pass",true);
    return new User("ashik","pass", new ArrayList<>());
    }
}

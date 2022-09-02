package com.example.order.services;

import com.example.order.entities.MyUserDetails;
import com.example.order.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//@Service
//public class MyUserDetailsService implements UserDetailsService{
//
//    @Autowired
//    private UsersService usersService;
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        MyUserDetails myUserDetails = usersService.getUserDetailsByUserName(userName);
//        return myUserDetails;
//    }
//
//
//
//}
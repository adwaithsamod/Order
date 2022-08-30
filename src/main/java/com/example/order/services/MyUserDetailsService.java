package com.example.order.services;

import com.example.order.entities.Gender;
import com.example.order.entities.MyUserDetails;
import com.example.order.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersService usersService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return (UserDetails) new Users("ashik","8989898989","adwaithsasmod@gmail.com",9, Gender.male,"India","ashik","pass",true);
        MyUserDetails userDetails = usersService.getUserDetailsByUserName(username);
        Users user = usersService.getUserByUserName(username);
//        return (UserDetails) new Users(user);

//        return (UserDetails) new Users(user);
        return userDetails;
//    return new User("ashik","pass", new ArrayList<>());
    }
}


package com.example.order.services;

import com.example.order.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private UsersService usersService;
    public void send(Long userId) {
        Users users = usersService.getUser(userId);
//        sendNotification(user.getPhoneNumber());
    }
}
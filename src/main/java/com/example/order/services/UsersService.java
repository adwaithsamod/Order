package com.example.order.services;

import com.example.order.controllers.exceptionHandling.EmptyInputException;
import com.example.order.controllers.request.AddressCreateRequest;
import com.example.order.controllers.request.UserCreateRequest;
import com.example.order.entities.DeliveryAddress;
import com.example.order.entities.MyUserDetails;
import com.example.order.entities.Users;
import com.example.order.repositories.UsersRepository;
import com.example.order.responseModel.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

    public Response addUsers(UserCreateRequest userCreateRequest) {
        List<DeliveryAddress> addresses = new ArrayList<>();
        try {
            for (AddressCreateRequest request : userCreateRequest.getAddressCreateRequest()) {
                DeliveryAddress address = new DeliveryAddress(
                        request.getHouse(),
                        request.getStreet(),
                        request.getCity(),
                        request.getPin(),
                        request.getState()
                );
                addresses.add(address);
            }
        }catch(NullPointerException e){
            addresses = null;
        }



//        for (AddressCreateRequest request : userCreateRequest.getAddressCreateRequest()) {
//            addresses.add(DeliveryAddress.builder()
//                    .pin(request.getPin())
//                    .State(request.getState())
//                    .house(request.getHouse())
//                    .street(request.getStreet())
//                    .city(request.getCity())
//                    .build());
//        }
        Users user = new Users(
                userCreateRequest.getName(),
                userCreateRequest.getUserPh(),
                userCreateRequest.getEmail(),
                addresses,
                userCreateRequest.getAge(),
                userCreateRequest.getGender(),
                userCreateRequest.getNationality(),
                userCreateRequest.getUserName(),
                cryptPasswordEncoder.encode(userCreateRequest.getPassword()),
                userCreateRequest.getAuthorities(),
                userCreateRequest.isEnabled()
        );

        if (user.getName().isEmpty()|| user.getName().length()==0||
                user.getPhoneNumber().isEmpty()|| user.getPhoneNumber().length()==0||
        user.getEmail().isEmpty()|| user.getEmail().length()==0||
        user.getNationality().isEmpty()|| user.getNationality().length()==0){
            throw new EmptyInputException();
        }

        try {
            usersRepository.save(user);

        }catch (DataIntegrityViolationException e){
            if(isPhoneNoAlreadyExist(userCreateRequest.getUserPh())){
                return new Response(false,"Phone No already Registered",null);
            }else{
                return new Response(false,"Email already Registered",null);
            }
        }
        walletService.createWallet(user);

        return new Response(true,"User Created", user);

    }

    private boolean isPhoneNoAlreadyExist(String userPh) {
        Users users = usersRepository.findByPhoneNumber(userPh);
        if(users ==null){
            return false;
        }
        return true;
    }

    public Users getUser(Long userId) {
        return usersRepository.findById(userId).get();
    }

    public List<Users> getAllUsers() {
        List<Users> userDetails = new ArrayList<>();
        usersRepository.findAll().forEach(userDetails::add);
        return userDetails;
    }


    public void addDeliveryAddressId(Long userId, DeliveryAddress deliveryAddress) {
        Users users = usersRepository.findById(userId).get();
        List<DeliveryAddress> deliveryAddressList = users.getDeliveryAddresses();
        deliveryAddressList.add(deliveryAddress);
        users.setDeliveryAddresses(deliveryAddressList);
        usersRepository.save(users);
    }

    public MyUserDetails getUserDetailsByUserName(String username) {
        Users user = (usersRepository.findByUserName(username));
        MyUserDetails myUserDetails = new MyUserDetails(user.getUserName(), user.getPassword(), user.getAuthorities(), user.isEnabled());

        return myUserDetails;
    }

    public Users getUserByUserName(String username){
        Users user = usersRepository.findByUserName(username);
        return user;
    }


}

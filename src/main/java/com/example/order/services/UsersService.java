package com.example.order.services;

import com.example.order.controllers.exceptionHandling.EmptyInputException;
import com.example.order.responseModel.Response;
import com.example.order.controllers.request.AddressCreateRequest;
import com.example.order.controllers.request.UserCreateRequest;
import com.example.order.entities.DeliveryAddress;
import com.example.order.entities.Users;
import com.example.order.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

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
        Users users = new Users(
                userCreateRequest.getName(),
                userCreateRequest.getUserPh(),
                userCreateRequest.getEmail(),
                addresses,
                userCreateRequest.getAge(),
                userCreateRequest.getGender(),
                userCreateRequest.getNationality()
        );

        if (users.getName().isEmpty()|| users.getName().length()==0||
                users.getPhoneNumber().isEmpty()|| users.getPhoneNumber().length()==0||
        users.getEmail().isEmpty()|| users.getEmail().length()==0||
        users.getNationality().isEmpty()|| users.getNationality().length()==0){
            throw new EmptyInputException();
        }

        try {
            usersRepository.save(users);
        }catch (DataIntegrityViolationException e){
            if(isPhoneNoAlreadyExist(userCreateRequest.getUserPh())){
                return new Response(false,"Phone No already Registered",null);
            }else{
                return new Response(false,"Email already Registered",null);
            }
        }
        walletService.createWallet(users);
        return new Response(true,"User Created", users);

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

    @Cacheable("users")
    public List<Users> getAllUsers() {
        List<Users> userDetails = new ArrayList<>();
        usersRepository.findAll().forEach(userDetails::add);
        return userDetails;
    }


    public void addDeliveryAddressId(Long userId, DeliveryAddress deliveryAddress) {
        Users users = usersRepository.findById(userId).get();
        List<DeliveryAddress> deliveryAddressList = users.getDeliveryAddress();
        deliveryAddressList.add(deliveryAddress);
        users.setDeliveryAddress(deliveryAddressList);
        usersRepository.save(users);
    }
}

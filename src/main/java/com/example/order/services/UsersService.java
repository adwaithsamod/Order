package com.example.order.services;

import com.example.order.controllers.exceptionHandling.EmptyInputException;
import com.example.order.controllers.request.AddressCreateRequest;
import com.example.order.controllers.request.UserCreateRequest;
import com.example.order.entities.DeliveryAddress;
import com.example.order.entities.MyUserDetails;
import com.example.order.entities.Role;
import com.example.order.entities.Users;
import com.example.order.repositories.UsersRepository;
import com.example.order.responseModel.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;



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
                passwordEncoder.encode(userCreateRequest.getPassword()),
//                userCreateRequest.getAuthorities(),
                userCreateRequest.isEnabled()
        );

        if (user.getName().isEmpty()|| user.getName().length()==0||
                user.getPhoneNumber().isEmpty()|| user.getPhoneNumber().length()==0||
                user.getEmail().isEmpty()|| user.getEmail().length()==0||
                user.getNationality().isEmpty()|| user.getNationality().length()==0){
            throw new EmptyInputException();
        }

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(user.getEmail().split("@")[1].equals("admin.edu")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        user.setRoles(roleSet);

        try {
            usersRepository.save(user);

        }catch (DataIntegrityViolationException e){
            if(isPhoneNoAlreadyExist(userCreateRequest.getUserPh())){
                return new Response(false,"Phone No already Registered",null);
            }else if(isEmailAlreadyRegistered(userCreateRequest.getEmail())){
                return new Response(false,"Email already Registered",null);
            }else{
                return new Response(false,"Username already Registered",null);
            }
            //check for email and username
        }
        walletService.createWallet(user);

        return new Response(true,"User Created", user);

    }

    private boolean isEmailAlreadyRegistered(String email) {
        Users users = usersRepository.findByEmail(email);
        if(users ==null){
            return false;
        }
        return true;
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
        List<Users> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }


    public void addDeliveryAddressId(Long userId, DeliveryAddress deliveryAddress) {
        Users users = usersRepository.findById(userId).get();
        List<DeliveryAddress> deliveryAddressList = users.getDeliveryAddresses();
        deliveryAddressList.add(deliveryAddress);
        users.setDeliveryAddresses(deliveryAddressList);
        usersRepository.save(users);
    }



//    public MyUserDetails getUserDetailsByUserName(String username) {
//        Users user = (usersRepository.findByUserName(username));
//        MyUserDetails myUserDetails = new MyUserDetails(user.getUserName(), user.getPassword(),  user.isEnabled());
//
//        return myUserDetails;
//    }



    public Users getUserByUserName(String username){
        Users user = usersRepository.findByUserName(username);
        return user;
    }


    private Set<SimpleGrantedAuthority> getAuthority(Users user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = getUserByUserName(username);
        if(user==null){
            throw  new UsernameNotFoundException("Invalid username or password");

        }
        return new User(user.getUserName(),user.getPassword(),getAuthority(user));
    }

}
package com.example.order.controllers.request;

import com.example.order.entities.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    private String name;
    private String userPh;
    private String email;

    private List<AddressCreateRequest> addressCreateRequest;
    private Integer age;
    private Gender gender;
    private String nationality;






}

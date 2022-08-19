package com.example.order.repositories;

import com.example.order.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users,Long> {
    Users findByPhoneNumber(String userPh);
}

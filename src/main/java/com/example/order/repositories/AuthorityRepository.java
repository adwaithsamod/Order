package com.example.order.repositories;

import com.example.order.entities.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authorities,Long> {
}

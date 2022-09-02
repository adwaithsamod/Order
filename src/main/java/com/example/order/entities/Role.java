package com.example.order.entities;

import com.example.order.auditable.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Role extends Auditable {
    @Id
    @GeneratedValue
    private Long authorityId;

    private String name;

//private String authority;
}
package com.example.order.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Authorities extends Auditable {
@Id
@GeneratedValue
private Long authorityId;

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "fk_users")
private Users user;

private String authority;
}


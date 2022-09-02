package com.example.order.entities;

import com.example.order.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class JwtToken extends Auditable {
    @Id
    @GeneratedValue
    private Long tokenId;

    private String username;

    private String token;



    public JwtToken(String username, String token) {
        this.username = username;
        this.token = token;

    }
}

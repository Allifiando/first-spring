package com.example.apicrud.pojo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @JsonProperty("roles")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Role> roleSet;

    @JsonProperty("car")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Car> carSet;

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, email=%s, username=%s, authorities=%s]",
                id,
                email,
                username,
                roleSet
        );
    }
}

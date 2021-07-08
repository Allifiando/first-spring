package com.example.apicrud.pojo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "car")
@Getter
@Setter
public class Car {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "car_name")
    private String carName;

    @Column(name = "type")
    private String type;

    @Column(name = "number")
    private String number;

    @Column(name = "color")
    private String color;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

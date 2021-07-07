package com.example.apicrud.pojo.dto;

import com.example.apicrud.pojo.models.Car;
import com.example.apicrud.pojo.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter

public class UserDTO {
    private User user;
    private List<Car> cars;

}

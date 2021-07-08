package com.example.apicrud.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("user_id")
    private int userId;
}

package com.example.apicrud.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    @JsonProperty("status_code")
    private Integer statusCode;

    @JsonProperty("message")
    private String message;
}

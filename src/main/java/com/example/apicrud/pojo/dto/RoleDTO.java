package com.example.apicrud.pojo.dto;

import com.example.apicrud.pojo.models.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDTO {
    @JsonProperty("role")
    private Integer roleId;

    @JsonProperty("role_name")
    private String roleName;

    @JsonProperty("user_id")
    private Integer userId;

}

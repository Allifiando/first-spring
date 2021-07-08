package com.example.apicrud.controllers;

import com.example.apicrud.error.UnexpectedException;
import com.example.apicrud.pojo.dto.RoleListDTO;
import com.example.apicrud.pojo.request.RoleRequest;
import com.example.apicrud.pojo.request.UserRequest;
import com.example.apicrud.pojo.response.UserResponse;
import com.example.apicrud.repository.RoleRepository;
import com.example.apicrud.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
public class RoleController {
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    public RoleController(RoleRepository roleRepository, RoleService roleService) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/role/")
    public ResponseEntity<?> create(@RequestBody RoleRequest request) throws Exception{
        UserResponse response = new UserResponse();
        String name = request.getName();
        Boolean doesEmailExists = roleService.doesRoleExists(name);
        if (doesEmailExists) {
            throw new IllegalArgumentException("Role already exist");
        }

        roleService.add(request);
        response.setStatusCode(200);
        response.setMessage("success");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/role/")
    public ResponseEntity<RoleListDTO> list(
            @RequestParam(value = "user_id", required = true) Integer id)
            throws Exception{
        Optional<RoleListDTO> roleListDTO = roleService.retrieveRoleListDto(id);
        if (!roleListDTO.isPresent()) {
            throw new UnexpectedException("Retrieve data list role failed");
        }

        return new ResponseEntity<>(roleListDTO.get(), HttpStatus.OK);
    }
}

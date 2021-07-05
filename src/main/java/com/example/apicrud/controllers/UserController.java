package com.example.apicrud.controllers;

import com.example.apicrud.entities.CarEntity;
import com.example.apicrud.entities.UserEntity;
import com.example.apicrud.repository.UserRepository;
import com.example.apicrud.security.TokenProvider;
import com.example.apicrud.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenProvider tokenProvider;

    @PostMapping("/register")
    public UserEntity update(@RequestBody UserEntity userEntity){
        UserEntity user = userService.register(userEntity);
        return user;
    }

    @PostMapping("login")
    public String login(@RequestBody UserEntity userEntity){
        UserEntity user = userRepository.findByEmail(userEntity.getEmail());
        log.info("data"+user.getUsername());
        if(user != null) {
            return tokenProvider.generateToken(user.getUsername());
        }else {
            return "error bro";
        }
    }
}

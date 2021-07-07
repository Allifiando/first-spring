package com.example.apicrud.controllers;

import com.example.apicrud.pojo.dto.UserDTO;
import com.example.apicrud.pojo.models.User;
import com.example.apicrud.pojo.request.UserRequest;
import com.example.apicrud.pojo.response.UserResponse;
import com.example.apicrud.repository.UserRepository;
import com.example.apicrud.security.TokenProvider;
import com.example.apicrud.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    public UserController(UserRepository userRepository, UserService userService, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/user/register")
    public ResponseEntity<?> update(@RequestBody UserRequest request){

        UserDTO dto = userService.register(request);
        UserResponse response = new UserResponse();
        response.setMessage("success");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/user/login")
    public String login(@RequestBody User userReq){
        User user = userRepository.findByEmail(userReq.getEmail());
        log.info("data"+user.getUsername());
        if(user.getUsername() != "") {
            return tokenProvider.generateToken(user.getUsername());
        }else {
            return "error bro";
        }
    }
}

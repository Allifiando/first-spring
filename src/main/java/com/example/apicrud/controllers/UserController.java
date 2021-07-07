package com.example.apicrud.controllers;

import com.example.apicrud.pojo.dto.JWTToken;
import com.example.apicrud.pojo.models.User;
import com.example.apicrud.pojo.request.UserRequest;
import com.example.apicrud.pojo.response.UserResponse;
import com.example.apicrud.repository.UserRepository;
import com.example.apicrud.security.JwtFilter;
import com.example.apicrud.security.TokenProvider;
import com.example.apicrud.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserController(UserRepository userRepository, UserService userService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/user/register")
    public ResponseEntity<?> update(@RequestBody UserRequest request) throws Exception{
        String email = request.getEmail();
        UserResponse response = new UserResponse();
        Boolean doesEmailExists = userService.doesEmailExists(email);
        if (doesEmailExists) {
            throw new IllegalArgumentException("User email is taken");
        }

        userService.register(request);
        response.setStatusCode(400);
        response.setMessage("success");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/user/ando")
    public ResponseEntity<JWTToken> login(@RequestBody User userReq) throws Exception{
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userReq.getEmail(), userReq.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("data",authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
    log.info("jwt controller",jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(JWTToken.builder().idToken(jwt).build(), httpHeaders,HttpStatus.OK);
    }
}

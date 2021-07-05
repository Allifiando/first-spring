package com.example.apicrud.services;

import com.example.apicrud.entities.CarEntity;
import com.example.apicrud.entities.UserEntity;
import com.example.apicrud.repository.CarRepository;
import com.example.apicrud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserSerciveImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserEntity register(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        log.info("data",userEntity);
        return userRepository.save(userEntity);
    }
}

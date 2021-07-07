package com.example.apicrud.services;
import com.example.apicrud.pojo.dto.UserDTO;
import com.example.apicrud.pojo.models.User;
import com.example.apicrud.pojo.request.UserRequest;
import com.example.apicrud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean doesEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDTO register(UserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return UserDTO.builder().user(user).build();
    }
}

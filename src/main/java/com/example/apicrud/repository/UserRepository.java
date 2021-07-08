package com.example.apicrud.repository;

import com.example.apicrud.pojo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    Optional<User> findOneByEmail(String email);
    Boolean existsByEmail(String email);
}

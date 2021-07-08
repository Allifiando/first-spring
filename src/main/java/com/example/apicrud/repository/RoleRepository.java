package com.example.apicrud.repository;

import com.example.apicrud.pojo.models.Role;
import com.example.apicrud.pojo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByUserId(Integer userId);
    Boolean existsByName(String name);
}

package com.example.apicrud.services;

import com.example.apicrud.pojo.dto.RoleDTO;
import com.example.apicrud.pojo.dto.RoleListDTO;
import com.example.apicrud.pojo.dto.UserDTO;
import com.example.apicrud.pojo.models.Role;
import com.example.apicrud.pojo.models.User;
import com.example.apicrud.pojo.request.RoleRequest;
import com.example.apicrud.repository.RoleRepository;
import com.example.apicrud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RoleService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Boolean doesRoleExists(String name) {
        String lowercase = name.toLowerCase(Locale.ENGLISH);
        return roleRepository.existsByName(lowercase);
    }

    public List<Role>findAllByUserId(Integer id){
        return roleRepository.findAllByUserId(id);
    }

    public RoleDTO add(RoleRequest request) {
        User user = userRepository.findById(request.getUserId()).get();

        Role role = new Role();
        role.setName(request.getName());
        role.setUser(user);
        roleRepository.save(role);
        return RoleDTO.builder().roleName(role.getName()).build();
    }

    public Optional<RoleListDTO> retrieveRoleListDto(Integer id) {
        RoleListDTO roleListDTO = new RoleListDTO();

        List<RoleDTO> roleDtoList = new ArrayList<>();
        List<Role> roleList = roleRepository.findAllByUserId(id);
        for (Role role : roleList) {
            RoleDTO roleDto = new RoleDTO();
            roleDto.setRoleId(role.getId());
            roleDto.setRoleName(role.getName());
            roleDto.setUserId(role.getUser().getId());
            roleDtoList.add(roleDto);
        }
        roleListDTO.setRoleDtoList(roleDtoList);

        return Optional.of(roleListDTO);
    }
}

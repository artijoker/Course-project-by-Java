package com.example.blog.domain.services;

import com.example.blog.domain.entities.Role;
import com.example.blog.domain.repositories.IRoleRepository;
import com.example.blog.http.models.responses.PostResponseModel;
import com.example.blog.http.models.responses.RoleResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final IRoleRepository roleRepository;


    @Autowired
    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleResponseModel> getRoles(){
        return roleRepository.findAll().stream().map(r -> {
           var model =  new RoleResponseModel();
            model.setRoleId(r.getId());
            model.setRoleName(r.getName());
            return model;
        }).toList();
    }
}

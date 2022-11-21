package com.example.blog.server.controllers.admin;

import com.example.blog.domain.entities.Role;
import com.example.blog.domain.services.PostService;
import com.example.blog.domain.services.RoleService;
import com.example.blog.http.models.responses.PostResponseModel;
import com.example.blog.http.models.responses.ResponseModel;
import com.example.blog.http.models.responses.RoleResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("admin/roles")
@CrossOrigin("*")
public class AdminRoleController  {
    private final RoleService roleService;

    @Autowired
    public AdminRoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("/get-roles")
    public ResponseModel<List<RoleResponseModel>> getRoles() {
        var response = new ResponseModel<List<RoleResponseModel>>();
        response.setSucceeded(true);
        response.setResult(roleService.getRoles());
        return response;
    }
}

package com.example.blog.domain.repositories;

import com.example.blog.domain.entities.Post;
import com.example.blog.domain.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IRoleRepository  extends CrudRepository<Role, Integer> {

    Optional<Role> findRoleByName(String name);
    List<Role> findAll();
    Role getRoleByName(String name);
    Role getRoleById(Integer id);
}

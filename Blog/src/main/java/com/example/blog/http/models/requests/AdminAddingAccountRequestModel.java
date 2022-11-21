package com.example.blog.http.models.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AdminAddingAccountRequestModel extends RegistrationRequestModel {
    @NotNull(message = "поле roleId не может быть пустым")
    private Integer roleId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}

package com.example.blog.http.models.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AdminUpdateAccountRequestModel {
    @NotNull(message = "поле accountId аккаунта не может быть пустым")
    private Integer accountId;
    @NotEmpty(message = "поле login аккаунта не может быть пустым")
    private String login;
    @NotEmpty(message = "поле email аккаунта не может быть пустым")
    private String email;
    private String newPassword;
    @NotNull(message = "поле roleId аккаунта не может быть пустым")
    private Integer roleId;
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}

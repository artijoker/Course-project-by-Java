package com.example.blog.http.models.requests;

import javax.validation.constraints.NotEmpty;

public class UpdateAccountRequestModel {
    @NotEmpty(message = "поле login не может быть пустым")
    private String login;
    @NotEmpty(message = "поле email не может быть пустым")
    private String email;
    private String newPassword;


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
}

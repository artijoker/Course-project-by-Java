package com.example.blog.http.models.requests;

import javax.validation.constraints.NotEmpty;

public class LogInRequestModel {
    @NotEmpty(message = "поле login не может быть пустым")
    private String login;
    @NotEmpty(message = "поле password не может быть пустым")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.blog.http.models.requests;

import javax.validation.constraints.NotEmpty;

public class RegistrationRequestModel extends LogInRequestModel {
    @NotEmpty(message = "поле email не может быть пустым")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

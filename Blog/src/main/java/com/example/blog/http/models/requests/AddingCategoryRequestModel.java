package com.example.blog.http.models.requests;

import javax.validation.constraints.NotEmpty;

public class AddingCategoryRequestModel {
    @NotEmpty(message = "поле categoryName категории не может быть пустым")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

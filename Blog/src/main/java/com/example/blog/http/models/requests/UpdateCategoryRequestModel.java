package com.example.blog.http.models.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateCategoryRequestModel extends AddingCategoryRequestModel{
    @NotNull(message = "поле categoryId категории не может быть пустым")
    private Integer categoryId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

}

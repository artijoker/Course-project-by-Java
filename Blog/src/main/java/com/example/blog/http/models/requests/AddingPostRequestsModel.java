package com.example.blog.http.models.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddingPostRequestsModel {
    @NotEmpty(message = "поле title не может быть пустым")
    private String title;
    @NotEmpty(message = "поле anons не может быть пустым")
    private String anons;
    @NotEmpty(message = "поле fullText не может быть пустым")
    private String fullText;
    @NotNull(message = "поле categoryId не может быть пустым")
    private Integer categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


}

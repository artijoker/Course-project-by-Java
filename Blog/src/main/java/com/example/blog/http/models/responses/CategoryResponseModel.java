package com.example.blog.http.models.responses;

import java.math.BigInteger;

public class CategoryResponseModel {
    private int categoryId;
    private String categoryName;
    private BigInteger quantityPublishedPosts;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigInteger getQuantityPublishedPosts() {
        return quantityPublishedPosts;
    }

    public void setQuantityPublishedPosts(BigInteger quantityPublishedPosts) {
        this.quantityPublishedPosts = quantityPublishedPosts;
    }
}
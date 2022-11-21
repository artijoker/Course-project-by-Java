package com.example.blog.http.models.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdatePostRequestModel extends AddingPostRequestsModel {
    @NotNull(message = "поле postId статьи не может быть пустым")
    private Integer postId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}

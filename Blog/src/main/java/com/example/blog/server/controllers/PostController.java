package com.example.blog.server.controllers;

import com.example.blog.domain.exceptions.NotFoundAccountException;
import com.example.blog.domain.exceptions.NotFoundCategoryException;
import com.example.blog.domain.exceptions.NotFoundPostException;
import com.example.blog.domain.exceptions.ValidationException;
import com.example.blog.http.models.requests.AddingPostRequestsModel;
import com.example.blog.http.models.requests.UpdatePostRequestModel;
import com.example.blog.http.models.responses.PostResponseModel;
import com.example.blog.http.models.responses.ResponseModel;
import com.example.blog.domain.services.PostService;

import com.example.blog.server.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin("*")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/get-posts")
    public ResponseModel<List<PostResponseModel>> getPosts() {
        var response = new ResponseModel<List<PostResponseModel>>();
        response.setSucceeded(true);
        response.setResult(postService.getPublishedPosts());

        return response;
    }

    @PostMapping("/get-author-posts")
    public ResponseModel<List<PostResponseModel>> getAuthorPosts(@RequestBody int accountId) {
        var response = new ResponseModel<List<PostResponseModel>>();
        response.setSucceeded(true);
        response.setResult(postService.getPublishedPostsByAccountId(accountId));

        return response;
    }

    @PostMapping("/get-post")
    public ResponseModel<PostResponseModel> getPostById(@RequestBody int postId)
            throws NotFoundPostException {
        var response = new ResponseModel<PostResponseModel>();
        var post = postService.getPostById(postId);
        response.setSucceeded(true);
        response.setResult(post);

        return response;
    }

    @PostMapping("/get-posts-by-category")
    public ResponseModel<List<PostResponseModel>> getPublishedPostsByCategoryId(@RequestBody int categoryId) {

        var response = new ResponseModel<List<PostResponseModel>>();
        response.setSucceeded(true);
        response.setResult(postService.getPublishedPostsByCategoryId(categoryId));

        return response;
    }
    @GetMapping("/get-draft-posts-by-account")
    public ResponseModel<List<PostResponseModel>> getDraftPostsByAccountId() {

        AccountDetails personDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var response = new ResponseModel<List<PostResponseModel>>();
        response.setSucceeded(true);
        response.setResult(postService.getDraftPostsByAccountId(personDetails.account().getId()));

        return response;
    }

    @GetMapping("/get-pending-posts-by-account")
    public ResponseModel<List<PostResponseModel>> getPendingPostsByAccountId() {

        AccountDetails personDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var response = new ResponseModel<List<PostResponseModel>>();
        response.setSucceeded(true);
        response.setResult(postService.getPendingPostsByAccountId(personDetails.account().getId()));

        return response;
    }

    @GetMapping("/get-published-posts-by-account")
    public ResponseModel<List<PostResponseModel>> getPublishedPostsByAccountId() {

        AccountDetails personDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var response = new ResponseModel<List<PostResponseModel>>();
        response.setSucceeded(true);
        response.setResult(postService.getPublishedPostsByAccountId(personDetails.account().getId()));

        return response;
    }

    @GetMapping("/get-rejected-posts-by-account")
    public ResponseModel<List<PostResponseModel>> getRejectedPostsByAccountId() {

        AccountDetails personDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var response = new ResponseModel<List<PostResponseModel>>();
        response.setSucceeded(true);
        response.setResult(postService.getRejectedPostsByAccountId(personDetails.account().getId()));

        return response;
    }

    @PostMapping("/add-post")
    public ResponseModel<?> addPost(@RequestBody @Valid AddingPostRequestsModel model,
                                    BindingResult bindingResult)
            throws NotFoundAccountException, NotFoundCategoryException, ValidationException {
        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        AccountDetails accountDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var response = new ResponseModel<>();

        postService.addDraftPost(accountDetails.account().getId(),
                model.getTitle(),
                model.getAnons(),
                model.getFullText(),
                model.getCategoryId()
        );
        response.setSucceeded(true);
        return response;
    }


    @PostMapping("/add-post-and-send-to-moderation")
    public ResponseModel<?> addPostAndSendToModeration(@RequestBody @Valid AddingPostRequestsModel model,
                                                       BindingResult bindingResult)
            throws NotFoundAccountException, NotFoundCategoryException, ValidationException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        AccountDetails accountDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var response = new ResponseModel<>();
        postService.addPostAndSendToModeration(accountDetails.account().getId(),
                model.getTitle(),
                model.getAnons(),
                model.getFullText(),
                model.getCategoryId()
        );
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/send-post-moderation")
    public ResponseModel<?> sendPostModeration(@RequestBody int postId)
            throws NotFoundPostException {
        var response = new ResponseModel<>();
        postService.sendPostModeration(postId);
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/send-post-to-draft")
    public ResponseModel<?> sendPostToDraft(@RequestBody int postId)
            throws NotFoundPostException {
        var response = new ResponseModel<>();
        postService.sendPostToDraft(postId);
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/update-post")
    public ResponseModel<?> updatePost(@RequestBody @Valid UpdatePostRequestModel model,
                                       BindingResult bindingResult)
            throws NotFoundPostException, NotFoundCategoryException, ValidationException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        var response = new ResponseModel<>();
        postService.userUpdatePost(model.getPostId(),
                model.getTitle(),
                model.getAnons(),
                model.getFullText(),
                model.getCategoryId()
        );
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/delete-post")
    public ResponseModel<?> removePost(@RequestBody int postId) {
        postService.removePost(postId);
        var response = new ResponseModel<>();
        response.setSucceeded(true);

        return response;
    }
}

package com.example.blog.server.controllers.admin;

import com.example.blog.domain.exceptions.NotFoundAccountException;
import com.example.blog.domain.exceptions.NotFoundCategoryException;
import com.example.blog.domain.exceptions.NotFoundPostException;
import com.example.blog.domain.exceptions.ValidationException;
import com.example.blog.domain.services.PostService;
import com.example.blog.http.models.requests.AddingPostRequestsModel;
import com.example.blog.http.models.requests.UpdatePostRequestModel;
import com.example.blog.http.models.responses.PostResponseModel;
import com.example.blog.http.models.responses.ResponseModel;
import com.example.blog.server.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("admin/posts")
@CrossOrigin("*")
public class AdminPostController {
    private final PostService postService;

    @Autowired
    public AdminPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/get-pending-posts")
    public ResponseModel<List<PostResponseModel>> getPendingPosts() {
        var response = new ResponseModel<List<PostResponseModel>>();
        response.setSucceeded(true);
        response.setResult(postService.getPendingPosts());
        return response;
    }

    @PostMapping("/add-post-and-published")
    public ResponseModel<?> addPostAndPublished(@RequestBody @Valid AddingPostRequestsModel model,
                                                BindingResult bindingResult)
            throws NotFoundAccountException, NotFoundCategoryException, ValidationException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        AccountDetails accountDetails = (AccountDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var response = new ResponseModel<>();
        postService.addPostAndPublished(accountDetails.account().getId(),
                model.getTitle(),
                model.getAnons(),
                model.getFullText(),
                model.getCategoryId()
        );
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/publish-post")
    public ResponseModel<?> publishPost(@RequestBody int postId)
            throws NotFoundPostException {
        var response = new ResponseModel<>();
        postService.publishPost(postId);
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/reject-post")
    public ResponseModel<?> rejectPost(@RequestBody int postId)
            throws NotFoundPostException {
        var response = new ResponseModel<>();
        postService.rejectPost(postId);
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
        postService.adminUpdatePost(model.getPostId(),
                model.getTitle(),
                model.getAnons(),
                model.getFullText(),
                model.getCategoryId()
        );
        response.setSucceeded(true);

        return response;
    }


}

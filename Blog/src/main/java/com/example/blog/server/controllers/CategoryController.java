package com.example.blog.server.controllers;

import com.example.blog.domain.exceptions.DefaultCategoryException;
import com.example.blog.domain.exceptions.DuplicateCategoryException;
import com.example.blog.domain.exceptions.NotFoundCategoryException;
import com.example.blog.domain.services.CategoryService;
import com.example.blog.http.models.requests.AddingCategoryRequestModel;
import com.example.blog.http.models.requests.UpdateCategoryRequestModel;
import com.example.blog.http.models.responses.CategoryResponseModel;
import com.example.blog.http.models.responses.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/get-categories")
    public ResponseModel<List<CategoryResponseModel>> getCategories() {
        var response = new ResponseModel<List<CategoryResponseModel>>();
        response.setSucceeded(true);
        response.setResult(categoryService.getCategoriesAndCountPublishedPostsForEach());

        return response;
    }

}

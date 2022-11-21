package com.example.blog.server.controllers.admin;

import com.example.blog.domain.exceptions.DefaultCategoryException;
import com.example.blog.domain.exceptions.DuplicateCategoryException;
import com.example.blog.domain.exceptions.NotFoundCategoryException;
import com.example.blog.domain.exceptions.ValidationException;
import com.example.blog.domain.services.CategoryService;
import com.example.blog.http.models.requests.AddingCategoryRequestModel;
import com.example.blog.http.models.requests.UpdateCategoryRequestModel;
import com.example.blog.http.models.responses.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("admin/categories")
@CrossOrigin("*")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add-new-category")
    public ResponseModel<?> AddCategory(@RequestBody @Valid AddingCategoryRequestModel model,
                                        BindingResult bindingResult)
            throws DuplicateCategoryException, ValidationException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        var response = new ResponseModel<>();
        categoryService.AddCategory(model.getCategoryName());
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/update-category")
    public ResponseModel<?> UpdateCategory(@RequestBody @Valid UpdateCategoryRequestModel model,
                                           BindingResult bindingResult)
            throws DefaultCategoryException, DuplicateCategoryException,
            NotFoundCategoryException, ValidationException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors());

        var response = new ResponseModel<>();
        categoryService.UpdateCategory(model.getCategoryId(), model.getCategoryName());
        response.setSucceeded(true);

        return response;
    }

    @PostMapping("/delete-category")
    public ResponseModel<?> RemoveCategory(@RequestBody int categoryId)
            throws DefaultCategoryException {
        var response = new ResponseModel<>();
        categoryService.RemoveCategory(categoryId);
        response.setSucceeded(true);

        return response;
    }

}

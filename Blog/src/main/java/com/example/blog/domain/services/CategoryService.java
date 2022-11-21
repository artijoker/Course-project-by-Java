package com.example.blog.domain.services;

import com.example.blog.domain.entities.Category;
import com.example.blog.domain.exceptions.DefaultCategoryException;
import com.example.blog.domain.exceptions.DuplicateCategoryException;
import com.example.blog.domain.exceptions.NotFoundCategoryException;
import com.example.blog.domain.repositories.ICategoryRepository;
import com.example.blog.domain.repositories.IPostRepository;
import com.example.blog.http.models.responses.CategoryResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;
    private final IPostRepository postRepository;
    @Autowired
    public CategoryService(ICategoryRepository categoryRepository,
                           IPostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public List<CategoryResponseModel> getCategories() {
        var categoryModels = new ArrayList<CategoryResponseModel>();
        categoryRepository.findAll().forEach(c ->{
            var model = new CategoryResponseModel();
            model.setCategoryId(c.getId());
            model.setCategoryName(c.getName());
            categoryModels.add(model);
        });
        return categoryModels;
    }
    public List<CategoryResponseModel> getCategoriesAndCountPublishedPostsForEach() {
        var categoryModels = new ArrayList<CategoryResponseModel>();
        var x =  categoryRepository.getCategoriesAndCountPostsForEachByStatusId(3);

        categoryRepository.getCategoriesAndCountPostsForEachByStatusId(3).forEach(objects ->{
            var model = new CategoryResponseModel();
            model.setCategoryId((Integer)(objects[0]));
            model.setCategoryName((String)(objects[1]));
            model.setQuantityPublishedPosts((BigInteger)(objects[2]));
            categoryModels.add(model);
        });
        return categoryModels;
    }

    public void AddCategory(String name) throws DuplicateCategoryException {
        if (categoryRepository.findCategoriesByName(name).isPresent())
            throw new DuplicateCategoryException();

        var category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    public void UpdateCategory(int categoryId, String name) throws DefaultCategoryException, DuplicateCategoryException, NotFoundCategoryException {
        if (categoryId == 1)
            throw new DefaultCategoryException();

        if (categoryRepository.findCategoriesByName(name).isPresent())
            throw new DuplicateCategoryException();

        var categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isEmpty())
            throw new NotFoundCategoryException();

        var category = categoryOptional.get();

        if (!Objects.equals(category.getName(), name) && !name.isBlank()){
            category.setName(name);
            categoryRepository.save(category);
        }
    }

    public void RemoveCategory(int categoryId) throws DefaultCategoryException {
        if (categoryId == 1)
            throw new DefaultCategoryException();

        var category = categoryRepository.getCategoriesById(1);
        var posts = postRepository.getPostsByCategoryId(categoryId);
        posts.forEach(p -> p.setCategory(category));
        postRepository.saveAll(posts);

        categoryRepository.deleteById(categoryId);
    }
}

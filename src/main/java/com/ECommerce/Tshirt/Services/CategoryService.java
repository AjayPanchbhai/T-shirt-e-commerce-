package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.Category;
import com.ECommerce.Tshirt.Repositories.CategoryRepository;
import com.ECommerce.Tshirt.Repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    // add Category
    public Category addCategory(@NotNull Category category) {
    // User user = userRepository.findById(userId)
    //      .orElseThrow(() -> new ResourceNotFoundException("User not found with ID : " + userId));

        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if (!authenticationService.isAdmin()) {
            throw new RuntimeException("User is not authorized to add a category");
        }

    //  if (!user.getRole().toString().equalsIgnoreCase("ADMIN"))
    //      throw new IllegalArgumentException("You are Not ADMIN!");

        if(categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists.");
        }

        return categoryRepository.save(category);
    }

    // get Category
    public Category getCategory(long categoryId) {

        return categoryRepository.findById(categoryId)
               .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID : " + categoryId));
    }

    // get all category
    public List<Category> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        if(categories.isEmpty()) {
            throw new ResourceNotFoundException("No Category Found!");
        }

        return categories;
    }

    // update category
    public Category updateCategory(long categoryId, @NotNull Category category) {

        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if (!authenticationService.isAdmin()) {
            throw new RuntimeException("User is not authorized to update a category");
        }

        Category category1 = this.getCategory(categoryId);

        category1.setName(category.getName());
        categoryRepository.save(category1);

        return category1;
    }

    // delete category
    public Category deleteCategory(long categoryId) {

        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if (!authenticationService.isAdmin()) {
            throw new RuntimeException("User is not authorized to delete a category");
        }

        Category category = this.getCategory(categoryId);

        categoryRepository.deleteById(categoryId);

        return category;
    }
}

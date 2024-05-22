package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.Category;
import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Repositories.CategoryRepository;
import com.ECommerce.Tshirt.Repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    // add Category
    public Category addCategory(long userId, @NotNull Category category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID : " + userId));

        if (authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        System.out.println(authenticationService.isSignedIn());
        System.out.println(authenticationService.isAdmin());

        if (authenticationService.isAdmin()) {
            throw new RuntimeException("User is not authorized to add a product");
        }

        if (!user.getRole().toString().equalsIgnoreCase("ADMIN"))
            throw new IllegalArgumentException("You are Not ADMIN!");

        if(categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists.");
        }

        return categoryRepository.save(category);
    }

    // get Category
    public Optional<Category> getCategory(long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    // get all category
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // update category
    public Optional<Category> updateCategory(long categoryId, Category category) {
        Category category1 = this.getCategory(categoryId).orElse(null);

        if(category1 != null) {
            if(category.getName() != null) category1.setName(category.getName());

            categoryRepository.save(category1);
        }

        return this.getCategory(categoryId);
    }

    // delete category
    public Optional<Category> deleteCategory(long categoryId) {
        Category category = this.getCategory(categoryId).orElse(null);
        if(category != null)
            categoryRepository.deleteById(categoryId);

        return Optional.ofNullable(category);
    }
}

package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.CategoryDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Mappers.CategoryMapper;
import com.ECommerce.Tshirt.Models.Category;
import com.ECommerce.Tshirt.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/{userId}")
    public ResponseEntity<CategoryDTO> addCategory(@PathVariable long userId, @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryMapper.toCategoryDTO(categoryService.addCategory(userId, category)));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable long categoryId) {
        Category category = categoryService.getCategory(categoryId).
                orElseThrow(() -> new ResolutionException("Category Not Found with ID : " + categoryId));

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(CategoryMapper.toCategoryDTO(category));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        if(categories.isEmpty()) {
            throw new ResourceNotFoundException("No Category Found!");
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(categories
                        .stream()
                        .map(CategoryMapper::toCategoryDTO)
                        .collect(Collectors.toList()));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable long categoryId, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(categoryId, category)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + categoryId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(CategoryMapper.toCategoryDTO(updatedCategory));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable long categoryId) {
        Category deletedCategory = categoryService.deleteCategory(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID " + categoryId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(CategoryMapper.toCategoryDTO(deletedCategory));
    }
}

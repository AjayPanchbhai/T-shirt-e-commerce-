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

    @PostMapping("")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody Category category) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryMapper.toCategoryDTO(categoryService.addCategory(category)));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable long categoryId) {

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(CategoryMapper.toCategoryDTO(categoryService.getCategory(categoryId)));
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        return ResponseEntity.status(HttpStatus.FOUND)
                .body( categoryService.getAllCategories().stream().map(CategoryMapper::toCategoryDTO).collect(Collectors.toList()));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory (
            @PathVariable long categoryId,
            @RequestBody Category category
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(CategoryMapper.toCategoryDTO(categoryService.updateCategory(categoryId, category)));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory (@PathVariable long categoryId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(CategoryMapper.toCategoryDTO(categoryService.deleteCategory(categoryId)));
    }
}

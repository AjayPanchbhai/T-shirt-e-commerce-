package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.Category;
import com.ECommerce.Tshirt.Models.File;
import com.ECommerce.Tshirt.Models.Product;
import com.ECommerce.Tshirt.Repositories.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthenticationService authenticationService;

    // Add product
    public Product addProduct(
            long categoryId,
            Product product,
            MultipartFile[] files
    ) throws IOException {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if (!authenticationService.isAdmin()) {
            throw new RuntimeException("User is not authorized to add a product");
        }

        Category category = categoryService.getCategory(categoryId);

        product.setCategory(category);

        for (MultipartFile file : files) {
            File savedFile = fileService.addFile(file);
            product.getPhotos().add(savedFile);
        }

        return productRepository.save(product);
    }

    // Get product
    public Product getProduct(long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found with ID : " + productId));
    }

    // Get all products
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    // Update product
    public Optional<Product> updateProduct(long productId, @NotNull Product product, MultipartFile[] files) throws IOException {
        Product existingProduct = this.getProduct(productId);

        if (product.getName() != null) existingProduct.setName(product.getName());
        if (product.getDescription() != null) existingProduct.setDescription(product.getDescription());
        if (product.getCategory() != null) existingProduct.setCategory(product.getCategory());
        if (product.getStock() != null) existingProduct.setStock(product.getStock());
        if (product.getSold() != null) existingProduct.setSold(product.getSold());

        if (files != null) {
            for (MultipartFile file : files) {
                File savedFile = fileService.addFile(file);
                existingProduct.getPhotos().add(savedFile);
            }
        }

        return Optional.of(productRepository.save(existingProduct));
    }

    // Delete product
    public Product deleteProduct(long productId) {
        Product product = this.getProduct(productId);
        productRepository.deleteById(productId);

        return product;
    }
}

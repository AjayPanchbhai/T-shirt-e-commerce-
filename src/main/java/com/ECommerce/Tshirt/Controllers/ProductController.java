package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.ProductDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Mappers.ProductMapper;
import com.ECommerce.Tshirt.Models.Product;
import com.ECommerce.Tshirt.Services.AuthenticationService;
import com.ECommerce.Tshirt.Services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/add/{categoryId}")
    public ResponseEntity<ProductDTO> addProduct(@PathVariable long categoryId,
                                              @RequestPart("product") String productJson,
                                              @RequestPart("files") MultipartFile[] files) throws IOException {
        // Parse product JSON string to Product object
        Product product = objectMapper.readValue(productJson, Product.class);

        // Save product with files
        Product savedProduct = productService.addProduct(categoryId, product, files);
        return ResponseEntity.ok(ProductMapper.toProductDTO(savedProduct));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable long productId) {
        Product product = productService.getProduct(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found with ID : " + productId));
        return ResponseEntity.ok(ProductMapper.toProductDTO(product));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(
                products.stream()
                        .map(ProductMapper::toProductDTO)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable long productId,
                                                           @RequestPart("product") Product product,
                                                           @RequestPart("files") MultipartFile[] files) throws IOException {
        Product updatedProduct = productService.updateProduct(productId, product, files)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found with ID : " + productId));
        return ResponseEntity.ok(ProductMapper.toProductDTO(updatedProduct));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Optional<Product>> deleteProduct(@PathVariable long productId) {
        Optional<Product> product = productService.deleteProduct(productId);
        return ResponseEntity.ok(product);
    }
}

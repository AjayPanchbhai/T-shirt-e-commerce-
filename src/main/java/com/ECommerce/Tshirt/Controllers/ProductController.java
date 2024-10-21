package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.ProductDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Mappers.ProductMapper;
import com.ECommerce.Tshirt.Models.Product;
import com.ECommerce.Tshirt.Services.AuthenticationService;
import com.ECommerce.Tshirt.Services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public ResponseEntity<ProductDTO> addProduct(
            @PathVariable long categoryId,
            @RequestPart("product") String productJson,
            @RequestPart("files") MultipartFile[] files
    ) throws IOException {

        // Parse product JSON string to Product object
        Product product = objectMapper.readValue(productJson, Product.class);

        // Save product with files
        Product savedProduct = productService.addProduct(categoryId, product, files);
        return ResponseEntity.ok().body(ProductMapper.toProductDTO(savedProduct));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable long productId
    ) {
        return ResponseEntity.ok().body(ProductMapper.toProductDTO(productService.getProduct(productId)));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(Math.max(1, page) - 1, size);

        return ResponseEntity.ok().body(productService
                .getAllProducts(pageable)
                .map(ProductMapper::toProductDTO));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable long productId,
            @RequestPart("product") Product product,
            @RequestPart("files") MultipartFile[] files
    ) throws IOException {
        return ResponseEntity.ok().body(ProductMapper.toProductDTO(productService.updateProduct( productId, product, files)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found with ID : " + productId))));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(
            @PathVariable long productId
    ) {
        return ResponseEntity.ok().body(ProductMapper.toProductDTO(productService.deleteProduct(productId)));
    }
}

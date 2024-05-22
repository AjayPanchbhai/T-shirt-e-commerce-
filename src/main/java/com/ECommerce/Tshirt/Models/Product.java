package com.ECommerce.Tshirt.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Product name is Required!")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne
    private Category category;

    @Min(0)
    private Integer stock;

    @Min(0)
    private Integer sold;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> photos = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product() {
        this.stock = 0;
        this.sold = 0;
    }
}

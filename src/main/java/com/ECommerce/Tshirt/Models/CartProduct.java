package com.ECommerce.Tshirt.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "cart_products")
@Data
@AllArgsConstructor
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long cardProductId;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Boolean isInOrder;

    private Boolean isInCart;

    private Integer count;

    public CartProduct() {
        this.count = 1;
        this.isInCart = true;
    }
}

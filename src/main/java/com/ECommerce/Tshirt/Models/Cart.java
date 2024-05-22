package com.ECommerce.Tshirt.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "carts")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", unique = true)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonManagedReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts;

    private Integer count;

    private Double amount;

    public Cart() {
        this.count = 0;
        this.amount = 0.0;
    }
}

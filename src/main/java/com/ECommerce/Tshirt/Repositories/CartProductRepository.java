package com.ECommerce.Tshirt.Repositories;

import com.ECommerce.Tshirt.Models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}

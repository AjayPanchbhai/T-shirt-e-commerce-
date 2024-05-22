package com.ECommerce.Tshirt.Repositories;

import com.ECommerce.Tshirt.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

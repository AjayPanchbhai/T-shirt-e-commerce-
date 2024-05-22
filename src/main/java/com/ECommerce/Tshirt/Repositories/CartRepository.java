package com.ECommerce.Tshirt.Repositories;

import com.ECommerce.Tshirt.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "select * from carts where user_id = ?1", nativeQuery = true)
    Cart findByUser(long userId);
}

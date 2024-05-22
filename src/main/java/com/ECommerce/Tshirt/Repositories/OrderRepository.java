package com.ECommerce.Tshirt.Repositories;

import com.ECommerce.Tshirt.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

package com.ECommerce.Tshirt.Mappers;

import com.ECommerce.Tshirt.DTO.OrderDTO;
import com.ECommerce.Tshirt.DTO.UserDTO;
import com.ECommerce.Tshirt.Models.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public static OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getOrderId(),
                new UserDTO(
                        order.getUser().getUserId(),
                        order.getUser().getFirstName(),
                        order.getUser().getLastName(),
                        order.getUser().getEmail(),
                        order.getUser().getRole()
                ),
                order.getProducts()
                        .stream()
                        .map(CartProductMapper::toCartProductDTO)
                        .collect(Collectors.toList()),
                order.getTransactionId(),
                order.getAmount(),
                order.getAddress(),
                order.getStatus()
        );
    }
}

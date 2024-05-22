package com.ECommerce.Tshirt.DTO;

import com.ECommerce.Tshirt.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private UserDTO user;
    private List<CartProductDTO> products;
    private String transactionId;
    private Double amount;
    private String address;
    private OrderStatus status;
}

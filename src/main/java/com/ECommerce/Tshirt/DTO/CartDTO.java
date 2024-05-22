package com.ECommerce.Tshirt.DTO;

import com.ECommerce.Tshirt.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartId;
    private UserDTO user;
    private List<CartProductDTO> products;
    private Integer count;
    private Double amount;
}

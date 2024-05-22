package com.ECommerce.Tshirt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDTO {
    private Long cartProductId;
    private ProductDTO product;
    private Integer count;
}

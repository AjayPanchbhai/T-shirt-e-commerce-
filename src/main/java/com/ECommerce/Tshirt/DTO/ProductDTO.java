package com.ECommerce.Tshirt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private CategoryDTO category;
    private Integer stock;
    private Integer sold;
    private List<FileDTO> photos;
}

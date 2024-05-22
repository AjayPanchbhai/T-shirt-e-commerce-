package com.ECommerce.Tshirt.Mappers;

import com.ECommerce.Tshirt.DTO.CategoryDTO;
import com.ECommerce.Tshirt.DTO.ProductDTO;
import com.ECommerce.Tshirt.Models.Product;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapper {

    @Contract("_ -> new")
    public static @NotNull ProductDTO toProductDTO(@NotNull Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                new CategoryDTO(
                        product.getCategory().getCategoryId(),
                        product.getCategory().getName()
                ),
                product.getStock(),
                product.getSold(),
                product.getPhotos().stream()
                        .map(FileMapper::toFileDTO)
                        .collect(Collectors.toList())
        );
    }
}

package com.ECommerce.Tshirt.Mappers;

import com.ECommerce.Tshirt.DTO.CartProductDTO;
import com.ECommerce.Tshirt.DTO.ProductDTO;
import com.ECommerce.Tshirt.DTO.CategoryDTO;
import com.ECommerce.Tshirt.Models.CartProduct;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartProductMapper {

    @Contract("_ -> new")
    public static @NotNull CartProductDTO toCartProductDTO(@NotNull CartProduct cartProduct) {
        return new CartProductDTO(
                cartProduct.getCardProductId(),
                new ProductDTO(
                        cartProduct.getProduct().getProductId(),
                        cartProduct.getProduct().getName(),
                        cartProduct.getProduct().getDescription(),
                        cartProduct.getProduct().getPrice(),
                        new CategoryDTO(
                                cartProduct.getProduct().getCategory().getCategoryId(),
                                cartProduct.getProduct().getCategory().getName()
                        ),
                        cartProduct.getProduct().getStock(),
                        cartProduct.getProduct().getSold(),
                        cartProduct.getProduct().getPhotos().stream()
                                .map(FileMapper::toFileDTO)
                                .collect(Collectors.toList())
                ),
                cartProduct.getCount()
        );
    }
}

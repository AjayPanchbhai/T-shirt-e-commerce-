package com.ECommerce.Tshirt.Mappers;

import com.ECommerce.Tshirt.DTO.CartDTO;
import com.ECommerce.Tshirt.DTO.UserDTO;
import com.ECommerce.Tshirt.Models.Cart;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    @Contract("_ -> new")
    public static @NotNull CartDTO toCartDTO(@NotNull Cart cart) {
        return new CartDTO(
                cart.getCartId(),
                new UserDTO (
                        cart.getUser().getUserId(),
                        cart.getUser().getFirstName(),
                        cart.getUser().getLastName(),
                        cart.getUser().getEmail(),
                        cart.getUser().getRole()
                ),
                cart.getCartProducts().stream()
                        .map(CartProductMapper::toCartProductDTO)
                        .collect(Collectors.toList()),
                cart.getCount(),
                cart.getAmount()
        );
    }
}

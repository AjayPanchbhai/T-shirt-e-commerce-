package com.ECommerce.Tshirt.Mappers;

import com.ECommerce.Tshirt.DTO.UserDTO;
import com.ECommerce.Tshirt.Models.User;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Contract("_ -> new")
    public static @NotNull UserDTO toUserDTO(@NotNull User user) {
        return new UserDTO (
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}

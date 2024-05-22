package com.ECommerce.Tshirt.Mappers;

import com.ECommerce.Tshirt.DTO.CategoryDTO;
import com.ECommerce.Tshirt.Models.Category;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    @Contract("_ -> new")
    public static @NotNull CategoryDTO toCategoryDTO(@NotNull Category category) {
        return new CategoryDTO (
                category.getCategoryId(),
                category.getName()
        );
    }
}

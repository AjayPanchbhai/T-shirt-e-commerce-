package com.ECommerce.Tshirt.Mappers;

import com.ECommerce.Tshirt.DTO.FileDTO;
import com.ECommerce.Tshirt.Models.File;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {
    @Contract("_ -> new")
    public static @NotNull FileDTO toFileDTO(@NotNull File file) {
        return new FileDTO(
                file.getFileId(),
                file.getFileName(),
                file.getFileType()
        );
    }
}

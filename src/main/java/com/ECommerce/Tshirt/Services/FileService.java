package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.File;
import com.ECommerce.Tshirt.Repositories.FileRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    // add File
    public File addFile(@NotNull MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        byte[] data = file.getBytes();

        File uploadfile = new File(fileName, fileType, data);
        return fileRepository.save(uploadfile);
    }

    // get File
    public File getFile(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with ID: " + fileId));
    }

    // get all files
    public List<byte[]> getAllFiles() {
        return fileRepository.findAll()
                .stream()
                .map(File::getData)
                .collect(Collectors.toList());
    }
}

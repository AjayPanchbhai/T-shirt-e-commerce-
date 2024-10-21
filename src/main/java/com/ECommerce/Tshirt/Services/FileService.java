package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.DTO.FileDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.File;
import com.ECommerce.Tshirt.Models.User;
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
    private final FileRepository fileRepository;
//    private final UserService userService;

    @Autowired
    public FileService(
//            UserService userService,
            FileRepository fileRepository
    ) {
//        this.userService = userService;
        this.fileRepository = fileRepository;
    }

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
    public List<File> getAllFiles() {
        List<File> files = fileRepository.findAll();

        if(files.isEmpty())
            throw new ResourceNotFoundException("No File Found!");

        return files;
    }

    public  List<File> getByFileNameAndFileType(@NotNull MultipartFile file) {
        return fileRepository.findByFileNameAndFileType(file.getOriginalFilename(), file.getContentType());
    }

    // delete file
    public File deleteFile(Long fileId) {
        File file = this.getFile(fileId);

//        List<User> users = userService.getUsersByFileId(fileId);

//        if (!users.isEmpty())
//            throw new RuntimeException("Can't remove this file!, it is being in use");

        fileRepository.deleteById(fileId);

        return file;
    }
}

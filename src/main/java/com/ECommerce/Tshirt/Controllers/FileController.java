package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.FileDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Mappers.FileMapper;
import com.ECommerce.Tshirt.Models.File;
import com.ECommerce.Tshirt.Services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File savedFile = fileService.addFile(file);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("File uploaded successfully: " + savedFile.getFileId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not upload the file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        File file = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, file.getFileType())
                .body(file.getData());
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        return ResponseEntity.status(HttpStatus.FOUND).body(
                fileService.getAllFiles()
                        .stream()
                        .map(FileMapper::toFileDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping()
    public ResponseEntity<List<FileDTO>> findByFileNameAndFileType(@RequestParam("profile") MultipartFile profile) {
        List<File> files = fileService.getByFileNameAndFileType(profile);

        if(files.isEmpty())
            throw new ResourceNotFoundException("No file found");

        return ResponseEntity.status(HttpStatus.FOUND).body(
                files.stream()
                        .map(FileMapper::toFileDTO)
                        .collect(Collectors.toList())
        );
    }
}

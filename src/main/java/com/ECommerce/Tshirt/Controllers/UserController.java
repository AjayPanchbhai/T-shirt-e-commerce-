package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.UserDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Mappers.UserMapper;
import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home() {
        return "Welcome to T-shirt Application";
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long userId) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(UserMapper.toUserDTO(userService.getUser(userId)));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        List<User> users = userService.getAllUsers();
        users = users.stream().peek(user -> {
            if (user.getProfile() != null) user.getProfile().setData(null);
        }).toList();

        return ResponseEntity.status(HttpStatus.FOUND).body(
                users
                .stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/files")
    public ResponseEntity<List<UserDTO>> getUsersByFileId(@RequestParam("fileId") Long fileId) {
        List<User> users = userService.getUsersByFileId(fileId);

        if(users.isEmpty())
            throw new ResourceNotFoundException("No user not FOUND!");

        return ResponseEntity.status(HttpStatus.FOUND).body(
                users.stream()
                        .map(UserMapper::toUserDTO)
                        .collect(Collectors.toList())
        );
    }

    @PatchMapping("/profile/{userId}")
    public ResponseEntity<UserDTO> updateUserProfile(
            @PathVariable long userId,
            @RequestBody MultipartFile profile
    ) throws IOException {
         return ResponseEntity.ok().body(UserMapper.toUserDTO(userService.updateUserProfile(userId, profile)));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long userId, @RequestBody User user) {

        return ResponseEntity.ok(UserMapper.toUserDTO(userService.updateUser(userId, user)));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable long userId) {

        return ResponseEntity.ok(UserMapper.toUserDTO(userService.deleteUser(userId)));
    }
}

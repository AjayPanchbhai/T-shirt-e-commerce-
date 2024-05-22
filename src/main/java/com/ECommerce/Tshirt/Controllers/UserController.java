package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.UserDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Helpers.passwordValidator.ValidPasswordHelper;
import com.ECommerce.Tshirt.Mappers.UserMapper;
import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserMapper.toUserDTO(userService.addUser(user)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long userId) {
        User user = userService.getUser(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID : " + userId));
        return ResponseEntity.status(HttpStatus.FOUND).body(UserMapper.toUserDTO(user));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No User Found!");
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList()));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + userId));

        return ResponseEntity.ok(UserMapper.toUserDTO(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable long userId) {
        User deletedUser = userService.deleteUser(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID " + userId));

        return ResponseEntity.ok(UserMapper.toUserDTO(deletedUser));
    }
}

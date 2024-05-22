package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    // get user
    public Optional<User> getUser(long userId) {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        return userRepository.findById(userId);
    }

    // get all users
    public List<User> getAllUsers() {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if (!authenticationService.isAdmin()) {
            throw new RuntimeException("User is not authorized to add a product");
        }

        return userRepository.findAll();
    }

    // update user
    public Optional<User> updateUser(long userId, User user) {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        User user1 = userRepository.findById(userId).orElse(null);

        if(user1 != null) {
            if(user.getFirstName() != null) user1.setFirstName(user.getFirstName());
            if(user.getLastName() != null) user1.setLastName(user.getLastName());
            if(user.getEmail() != null) user1.setEmail(user.getEmail());
            if(user.getPassword() != null) user1.setPassword(user.getPassword());
            if(user.getRole() != null) user1.setRole(user.getRole());

            userRepository.save(user1);
        }

        return userRepository.findById(userId);
    }

    // delete user
    public Optional<User> deleteUser(long userId) {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if (!authenticationService.isAdmin()) {
            throw new RuntimeException("User is not authorized to add a product");
        }

        User user = this.getUser(userId).orElse(null);

        if(user != null) {
            userRepository.deleteById(userId);
            return Optional.of(user);
        }

        return Optional.empty();
    }
}

package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.File;
import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final AuthenticationService authenticationService;
    private final FileService fileService;
    private final UserRepository userRepository;

    @Autowired
    public UserService(
            UserRepository userRepository,
            AuthenticationService authenticationService,
            FileService fileService
    ) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.authenticationService = authenticationService;
    }


    // get user
    public User getUser(long userId) {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID : " + userId));
    }

    // get all users
    public List<User> getAllUsers() {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if (!authenticationService.isAdmin()) {
            throw new RuntimeException("You are not authorized to get/see other user details!");
        }

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No User Found!");
        }

        return users;
    }

    // update user profile
    public User updateUserProfile(long userId, MultipartFile profile) throws IOException {
        User user = this.getUser(userId);

        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if(profile == null)
            throw new IllegalArgumentException("Profile might be Empty!, File is Required!");


        List<File> files = fileService.getByFileNameAndFileType(profile);

        if(files.isEmpty()) {
            File savedFile = fileService.addFile(profile);
            user.setProfile(savedFile);
        }

        return userRepository.save(user);
    }

    // update user
    public User updateUser(long userId, User user) {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        User user1 = this.getUser(userId);

        if(user1 != null) {
            if(user.getFirstName() != null) user1.setFirstName(user.getFirstName());
            if(user.getLastName() != null) user1.setLastName(user.getLastName());
            if(user.getEmail() != null) user1.setEmail(user.getEmail());
            if(user.getPassword() != null) user1.setPassword(user.getPassword());
            if(user.getRole() != null) user1.setRole(user.getRole());

            userRepository.save(user1);
        }

        return user1;
    }

    // delete user
    public User deleteUser(long userId) {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if (!authenticationService.isAdmin()) {
            throw new RuntimeException("User is not authorized to add a product");
        }

        User user = this.getUser(userId);
        userRepository.deleteById(userId);

        return user;
    }

    public List<User> getUsersByFileId(Long fileId) {
        return userRepository.findByProfile(fileId);
    }
}

package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Helpers.passwordValidator.ValidPasswordHelper;
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

    // add user
    public User addUser(User user) {
        if(userRepository.existsByEmail(user.getEmail()))
            throw new IllegalArgumentException("User with Email " + user.getEmail() + " already exists.");

        ValidPasswordHelper validPasswordHelper = new ValidPasswordHelper();
        String is = validPasswordHelper.isValid(user.getPassword());

        if(!is.equalsIgnoreCase("valid"))
            throw new IllegalArgumentException(is);

        userRepository.save(user);

        return user;
    }

    // get user
    public Optional<User> getUser(long userId) {
        return userRepository.findById(userId);
    }

    // get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // update user
    public Optional<User> updateUser(long userId, User user) {
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
        User user = this.getUser(userId).orElse(null);

        if(user != null) {
            userRepository.deleteById(userId);
            return Optional.of(user);
        }

        return Optional.empty();
    }
}

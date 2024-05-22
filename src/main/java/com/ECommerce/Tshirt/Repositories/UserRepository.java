package com.ECommerce.Tshirt.Repositories;

import com.ECommerce.Tshirt.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByFirstName(String firstName);
    Optional<User> findByEmail(String email);
}

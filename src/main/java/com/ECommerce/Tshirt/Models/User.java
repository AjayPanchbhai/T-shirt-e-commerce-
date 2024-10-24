package com.ECommerce.Tshirt.Models;

import com.ECommerce.Tshirt.Enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First Name is required!")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique=true, length=150, nullable=false)
    @NotBlank(message = "Email is Required. Shouldn't be Empty!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email format is invalid!")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is required!")
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "address", nullable = false)
    private String address;

    @JoinColumn(name = "profile")
    @OneToOne
    private File profile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Cart cart;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User() {
        this.role = Role.USER;
    }

    @PrePersist
    private void onCreate() {
        if (this.cart == null) {
            this.cart = new Cart();
            this.cart.setUser(this);
        }
    }
}

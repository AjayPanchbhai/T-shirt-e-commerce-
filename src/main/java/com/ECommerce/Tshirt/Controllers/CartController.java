package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.CartDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Mappers.CartMapper;
import com.ECommerce.Tshirt.Models.Cart;
import com.ECommerce.Tshirt.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartService cartService;


    @PostMapping("/{userId}")
    public ResponseEntity<CartDTO> addCart(@PathVariable long userId, @RequestBody Cart cart) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CartMapper.toCartDTO(cartService.addCart(userId, cart)));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable long cartId) {
        Cart cart = cartService.getCart(cartId).
                orElseThrow(() -> new ResolutionException("Cart Not Found with ID : " + cartId));

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(CartMapper.toCartDTO(cart));
    }

    @GetMapping("")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();

        if(carts.isEmpty()) {
            throw new ResourceNotFoundException("No Cart Found!");
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .body(carts.stream()
                        .map(CartMapper::toCartDTO)
                        .collect(Collectors.toList()));
    }

    @PatchMapping("/{userId}/{productId}/{isAdd}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable long userId, @PathVariable long productId, @PathVariable boolean isAdd) {
        Cart updatedCart = cartService.updateCart(userId, productId, isAdd)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with User ID " + userId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(CartMapper.toCartDTO(updatedCart));
    }
}

package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.CartDTO;
import com.ECommerce.Tshirt.DTO.CartProductDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Mappers.CartMapper;
import com.ECommerce.Tshirt.Mappers.CartProductMapper;
import com.ECommerce.Tshirt.Models.CartProduct;
import com.ECommerce.Tshirt.Services.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cartProducts")
public class CartProductController {
    @Autowired
    private CartProductService cartProductService;

    // add product to cart
    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<CartDTO> addToCart(@PathVariable long userId, @PathVariable long productId) {
         return ResponseEntity.status(HttpStatus.OK).body(
                 CartMapper.toCartDTO(
                         cartProductService.addToCart(userId, productId)));
    }

    // get list of the cart products
    @GetMapping("")
    public ResponseEntity<List<CartProductDTO>> getAllCartProducts() {
        List<CartProduct> products = cartProductService.getAllCartProducts();

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No Cart Product Found!");
        }

        List<CartProductDTO> cartProductDTOS = products.stream()
                .map(CartProductMapper::toCartProductDTO)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.FOUND).body(cartProductDTOS);
    }
}

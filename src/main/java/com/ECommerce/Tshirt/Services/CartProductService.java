package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.Cart;
import com.ECommerce.Tshirt.Models.CartProduct;
import com.ECommerce.Tshirt.Models.Product;
import com.ECommerce.Tshirt.Repositories.CartProductRepository;
import com.ECommerce.Tshirt.Repositories.CartRepository;
import com.ECommerce.Tshirt.Repositories.ProductRepository;
import com.ECommerce.Tshirt.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartProductService {
    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // Add product to cart
    public Cart addToCart(long userId, long productId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        Cart cart = cartRepository.findByUser(userId);

        if (cart == null) {
            throw new IllegalArgumentException("Cart with user ID: " + userId + " does not exist.");
        }

        Optional<CartProduct> existingCartProductOpt = cart.getCartProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct().getProductId() == productId)
                .findFirst();

        if(existingCartProductOpt.isPresent())
            throw new IllegalArgumentException("Cart Product with product ID : " + productId + " already exists");

        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);

        cartProductRepository.save(cartProduct);
        cart.setAmount(cart.getAmount() + product.getPrice());
        cart.getCartProducts().add(cartProduct);
        cart.setCount(cart.getCartProducts().size());

        return cartRepository.save(cart);
    }

    // Get list of the cart products
    public List<CartProduct> getAllCartProducts() {
        return cartProductRepository.findAll();
    }
}

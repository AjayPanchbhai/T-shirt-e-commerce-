package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.Cart;
import com.ECommerce.Tshirt.Models.CartProduct;
import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Repositories.CartRepository;
import com.ECommerce.Tshirt.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    // add Cart
    @Transactional
    public Cart addCart(long userId, Cart cart) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID : " + userId));
        System.out.println(user);

        List<Cart> carts = this.getAllCarts();
        if(!carts.isEmpty() && cartRepository.findByUser(userId) != null) {
            throw new IllegalArgumentException("Cart with user ID : " + userId + " already exists.");
        }

        cart.setUser(user);
        return cartRepository.save(cart);
    }

    // get cart
    public Optional<Cart> getCart(long cartId) {
        return cartRepository.findById(cartId);
    }

    // get cart by user
    public Optional<Cart> getCartByUser(long userId) {
        return Optional.ofNullable(cartRepository.findByUser(userId));
    }

    // get all cart
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    // update cart
    public Optional<Cart> updateCart(long userId, long productId, boolean isAdd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with ID : " + userId));

        Cart cart = user.getCart();
        List<CartProduct> cartProducts = cart.getCartProducts();

        CartProduct targetProduct = null;
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().getProductId() == productId) {
                targetProduct = cartProduct;
                break;
            }
        }

        if(targetProduct == null)
            throw new ResourceNotFoundException("Cart Product is Not Found of ID : " + productId);

        if (isAdd) {
            targetProduct.setCount(targetProduct.getCount() + 1);
            cart.setAmount(cart.getAmount() + targetProduct.getProduct().getPrice());
        } else {
            double productPrice = targetProduct.getProduct().getPrice();
            if (targetProduct.getCount() > 1) {
                targetProduct.setCount(targetProduct.getCount() - 1);
                cart.setAmount(cart.getAmount() - productPrice);
            } else {
                targetProduct.setIsInCart(false);
                cartProducts.remove(targetProduct);
                cart.setAmount(cart.getAmount() - productPrice);
            }
        }

        cart.setCount(cartProducts.size());

        return Optional.of(cartRepository.save(cart));
    }
}

package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.Cart;
import com.ECommerce.Tshirt.Models.CartProduct;
import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Repositories.CartRepository;
import com.ECommerce.Tshirt.Repositories.UserRepository;
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

    @Autowired
    private AuthenticationService authenticationService;

    // get cart
    public Cart getCart(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID : " + userId));

        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        return user.getCart();
    }

    // get all cart
    public List<Cart> getAllCarts() {
        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        if (!authenticationService.isAdmin()) {
            throw new RuntimeException("User is not authorized to add a product");
        }

        return cartRepository.findAll();
    }

    // update cart
    // adding product/cartProduct or remove
    public Optional<Cart> updateCart(long userId, long productId, boolean isAdd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with ID : " + userId));

        if (!authenticationService.isSignedIn()) {
            throw new RuntimeException("User is not signed in");
        }

        Cart cart = user.getCart();
        List<CartProduct> cartProducts = cart.getCartProducts();

        // finding given product exists or not
        CartProduct targetProduct = null;
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().getProductId() == productId) {
                targetProduct = cartProduct;
                break;
            }
        }

        if(targetProduct == null)
            throw new ResourceNotFoundException("Cart Product is Not Found of ID : " + productId);

        // adding product to cart
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

package com.ECommerce.Tshirt.Services;

import com.ECommerce.Tshirt.DTO.OrderDTO;
import com.ECommerce.Tshirt.Exceptions.ResourceNotFoundException;
import com.ECommerce.Tshirt.Models.Cart;
import com.ECommerce.Tshirt.Models.Order;
import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Repositories.CartRepository;
import com.ECommerce.Tshirt.Repositories.OrderRepository;
import com.ECommerce.Tshirt.Repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    // made order
    public Order addOrder(long userId, Order order) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID : " + userId));

        Cart cart = cartRepository.findByUser(userId);

        if(cart == null)
            throw new ResourceNotFoundException("Cart Not found with User " + userId);

        cart.getCartProducts().forEach(cartProduct -> cartProduct.setIsInOrder(true));
        order.getProducts().addAll(cart.getCartProducts());
        order.setUser(user);
        order.setAmount(cart.getAmount());

        return orderRepository.save(order);
    }

    // update Order
    public Order updateOrder(long orderId, @NotNull Order order) {
        Order order1 = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order is Not Found with ID : " + orderId));

        if(order.getTransactionId() != null) order1.setTransactionId(order.getTransactionId());
        if(order.getStatus() != null) order1.setStatus(order.getStatus());

        return orderRepository.save(order1);
    }

    // get All orders
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        if(orders.isEmpty())
            throw new ResourceNotFoundException("No Order is Found!");

        return orders;
    }
}

package com.store.services;

import org.springframework.stereotype.Service;

import com.store.dtos.CheckoutRequest;
import com.store.dtos.CheckoutResponse;
import com.store.entities.Order;
import com.store.exceptions.CartEmptyException;
import com.store.exceptions.CartNotFoundException;
import com.store.repositories.CartRepository;
import com.store.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CheckoutService {
	private final CartRepository cartRepository;
	private final OrderRepository orderRepository;
	private final AuthService authService;
	private final CartService cartService;
	
	public CheckoutResponse checkout(CheckoutRequest request) {
		var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
		if(cart == null) {
			throw new CartNotFoundException();
		}
		
		if(cart.isEmpty()) {
			throw new CartEmptyException();
		}
		
		var order = Order.fromCart(cart, authService.getCurrentUser());
		
		orderRepository.save(order);
		cartService.clearCart(cart.getId());
		
		return new CheckoutResponse(order.getId());
	}
}

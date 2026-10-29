package com.store.services;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.store.dtos.OrderDto;
import com.store.exceptions.OrderNotFoundException;
import com.store.mappers.OrderMapper;
import com.store.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {
	private final AuthService authService;
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	
	public List<OrderDto> getAllOrders(){
		var user = authService.getCurrentUser();
		var orders = orderRepository.getOrdersByCustomer(user);
		
		return orders.stream().map(orderMapper::toDto).toList();
	}
	
	public OrderDto getOrder(Long orderId) {
		var order = orderRepository
				.getOrderWithItems(orderId)
				.orElseThrow(() -> new OrderNotFoundException());
		var user = authService.getCurrentUser();
		if(!order.isPlacedBy(user)) {
			throw new AccessDeniedException("You don't have access to this order.");
		}
		
		return orderMapper.toDto(order);
	}
}

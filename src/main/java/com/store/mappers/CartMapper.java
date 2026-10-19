package com.store.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.store.dtos.CartDto;
import com.store.dtos.CartItemDto;
import com.store.entities.Cart;
import com.store.entities.CartItem;

@Mapper(componentModel = "spring")
public interface CartMapper {
	@Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);
    
    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}

package com.store.mappers;

import org.mapstruct.Mapper;

import com.store.dtos.OrderDto;
import com.store.entities.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	OrderDto toDto(Order order);
}

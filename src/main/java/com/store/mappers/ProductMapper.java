package com.store.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.store.dtos.ProductDto;
import com.store.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	@Mapping(target = "categoryId", source = "category.id")
	ProductDto toDto(Product product);
	
	Product toEntity(ProductDto productDto);

	@Mapping(target = "id", ignore = true)
	void update(ProductDto productDto, @MappingTarget Product product);
}

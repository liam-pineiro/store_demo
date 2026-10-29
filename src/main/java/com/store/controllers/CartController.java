package com.store.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.store.dtos.AddItemToCartRequest;
import com.store.dtos.CartDto;
import com.store.dtos.CartItemDto;
import com.store.dtos.ErrorDto;
import com.store.dtos.UpdateCartItemRequest;
import com.store.exceptions.CartNotFoundException;
import com.store.exceptions.ProductNotFoundException;
import com.store.services.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
	private final CartService cartService;
	
	@PostMapping
	public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){
		var cartDto = cartService.createCart();
		
		var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(cartDto);
	}
	
	@PostMapping("/{cartId}/items")
	@Operation(summary = "Adds a product to the cart")
	public ResponseEntity<CartItemDto> addToCart(@Parameter(description = "The ID of the cart.")
	@PathVariable UUID cartId, @RequestBody AddItemToCartRequest request){
		var cartItemDto = cartService.addToCart(cartId, request.getProductId());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
	}
	
	@GetMapping("/{cartId}")
	public CartDto getCart(@PathVariable UUID cartId){
		return cartService.getCart(cartId);
	}
	
	@PutMapping("/{cartId}/items/{productId}")
	public CartItemDto updateItem(@PathVariable("cartId") UUID cartId, @PathVariable("productId") Long productId, @Valid @RequestBody UpdateCartItemRequest request){
		return cartService.updateItem(cartId, productId, request.getQuantity());
	}
	
	@DeleteMapping("/{cartId}/items/{productId}")
	public ResponseEntity<?> removeItem(@PathVariable("cartId") UUID cartId, @PathVariable("productId") Long productId){
		cartService.deleteItem(cartId, productId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{cartId}/items")
	public ResponseEntity<Void> clearCart(@PathVariable ("cartId") UUID cartId){
		cartService.clearCart(cartId);
		
		return ResponseEntity.noContent().build();
	}
	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<ErrorDto> handleCartNotFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Cart was not found."));
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorDto> handleProductNotFound(){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Product was not found in the cart."));
	}	
}
	
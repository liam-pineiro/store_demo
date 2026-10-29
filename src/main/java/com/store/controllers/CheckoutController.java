package com.store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dtos.CheckoutRequest;
import com.store.dtos.CheckoutResponse;
import com.store.dtos.ErrorDto;
import com.store.exceptions.CartEmptyException;
import com.store.exceptions.CartNotFoundException;
import com.store.services.CheckoutService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {
	private final CheckoutService checkoutService;
	
	@PostMapping
	public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request){
		return checkoutService.checkout(request);
	}
	
	@ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
	public ResponseEntity<ErrorDto> handleException(Exception e){
		return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
	}
}

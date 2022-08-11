package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grocery.model.Cart;

@Service
public interface CartService {
	public List<Cart> getAllCartItem();

	public Optional<Cart> getCartByUserId(Long userId);

	public Cart addToCart(Cart cart);
	
	public Cart updateCart(Cart cart, int cartId);
	
	public Object deleteCartItem(int cartId);
}

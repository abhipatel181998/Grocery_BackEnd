package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grocery.model.Cart;

@Service
public interface CartService {
	public List<Cart> getAllCart();

	public Optional<Cart> getCartById(Long cartId);

	public Cart addCart(Cart cart);

	public Cart updateCart(Cart cart, Long cartId);

	public Object deleteCart(Long cartId);
}

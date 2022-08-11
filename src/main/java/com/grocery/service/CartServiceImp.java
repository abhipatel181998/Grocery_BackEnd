package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grocery.model.Cart;
import com.grocery.repository.CartRepository;

@Component
public class CartServiceImp {

	@Autowired
	CartRepository cartRepository; 
	
	/**
	 * 
	 * @return 
	 */
	public List<Cart> getAllCartItem(){
		return (List<Cart>) cartRepository.findAll();
	}
	
	
	public Optional<Cart> getCartByUserId(Long userId) {
		return cartRepository.findById(userId);
	}

	/**
	 * Create new cart.
	 * 
	 * @param user
	 * @return saved User object
	 */
	public Cart addToCart(Cart cart) {
		return cartRepository.save(cart);
	}

	/**
	 * Update cart by id.
	 * 
	 * @param cart
	 * @param cartId
	 * @return updated Cart object.
	 */
	public Cart updateCart(Cart cart, Long cartId) {
		Optional<Cart> cartData = cartRepository.findById(cartId);

		if (cartData.isPresent()) {
			return cartRepository.save(cart);
		}

		return null;
	}

	/**
	 * Delete cart by id.
	 * 
	 * @param cartId
	 * @return deleted Cart id or null
	 */
	public Object deleteCartItem(Long cartId) {
		Optional<Cart> cart = cartRepository.findById(cartId);

		if (cart.isPresent()) {
			cartRepository.deleteById(cartId);
			return cartId;
		}

		return null;
	}
	
}

package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grocery.model.Cart;
import com.grocery.repository.CartRepository;

@Component
public class CartServiceImp implements CartService {

	@Autowired
	CartRepository cartRepository;

	/**
	 * Get all the cart.
	 * 
	 * @return (List<Cart> Cart list.
	 */
	public List<Cart> getAllCart() {
		return (List<Cart>) cartRepository.findAll();
	}

	/**
	 * Get Cart by cart id.
	 * 
	 * @param cartId
	 * @return Optional<Cart> object.
	 */
	public Optional<Cart> getCartById(Long cartId) {
		return cartRepository.findById(cartId);
	}

	/**
	 * Save Cart.
	 * 
	 * @param cart
	 * @return saved Cart object.
	 */
	public Cart addCart(Cart cart) {
		if(cartRepository.findByUser(cart.getUser()) != null) {
			Optional<Cart> cartData = cartRepository.findByUser(cart.getUser());
		}
		return cartRepository.save(cart);
	}

	/**
	 * Update cart by id.
	 * 
	 * @param cart   Object of Cart
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
	public Object deleteCart(Long cartId) {
		Optional<Cart> cartData = cartRepository.findById(cartId);

		if (cartData.isPresent()) {
			cartRepository.deleteById(cartId);
			return cartId;
		}

		return null;
	}

}

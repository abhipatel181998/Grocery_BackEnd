package com.grocery.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.model.Cart;
import com.grocery.service.CartService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class CartController {
	@Autowired
	CartService cartService;

	/**
	 * @return all the carts.
	 */
	@GetMapping("/cart")
	public ResponseEntity<?> getCart() {
		try {
			log.info("Get All Cart Accessed.");
			return new ResponseEntity<>(cartService.getAllCart(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting cart.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param cartId
	 * @return Cart object found by cartId.
	 */
	@GetMapping("/cart/{id}")
	public ResponseEntity<?> getCartById(@PathVariable(name = "id", required = true) Long cartId) {
		try {
			Optional<Cart> cart = cartService.getCartById(cartId);

			if (cart.isPresent()) {
				log.info("Cart found for id: " + cartId);
				return new ResponseEntity<>(cart, HttpStatus.FOUND);
			} else {
				log.error("Cart not found for id: " + cartId);
				return new ResponseEntity<>("Cart not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting cart.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Add new cart in the database.
	 * 
	 * @param cart
	 * @return HttpStatus with with cart object or error message.
	 */
	@PostMapping("/cart")
	public ResponseEntity<?> addCart(@RequestBody Cart cart) {
		try {
			var response = cartService.addCart(cart);
			log.info("Cart added :" + response);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while creating cart.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update item in the database.
	 * 
	 * @param cart
	 * @param caartId
	 * @return HttpStatus with with cart object or error message.
	 */
	@PutMapping("/cart/{id}")
	public ResponseEntity<?> updateCart(@RequestBody Cart cart,
			@PathVariable(name = "id", required = true) Long cartId) {

		try {
			var response = cartService.updateCart(cart, cartId);
			log.info("cart updated: " + response);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (NullPointerException ne) {
			log.error("Cart not found for id: " + cartId);
			return new ResponseEntity<>("Cart not found!", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while updating cart.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update cart in the database.
	 * 
	 * @param cartId
	 * @return HttpStatus with with cart id or error message.
	 */
	@DeleteMapping("/cart/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable(name = "id", required = true) Long cartId) {

		try {
			var response = cartService.deleteCart(cartId);
			log.info("Cart deleted with id: " + response);

			if (response != null)
				return new ResponseEntity<>(response, HttpStatus.OK);
			else {
				log.error("Cart not found for id: " + cartId);
				return new ResponseEntity<>("Cart not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while deleting Cart.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}

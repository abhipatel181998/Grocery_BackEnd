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

import com.grocery.model.Wishlist;
import com.grocery.service.WishlistService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/wishlist")
@Log4j2
public class WishlistController {

	@Autowired
	WishlistService wishlistService;

	/**
	 * @return all the wishlists.
	 */
	@GetMapping("")
	public ResponseEntity<?> getWishlists() {
		try {
			log.info("Get All Wishlists Accessed.");
			return new ResponseEntity<>(wishlistService.getAllWishlists(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting wishlists.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param wishlistId
	 * @return Wishlist object found by wishlistId.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getWishlistById(@PathVariable(name = "id", required = true) Long wishlistId) {
		try {
			Optional<Wishlist> wishlist = wishlistService.getWishlistById(wishlistId);

			if (wishlist.isPresent()) {
				log.info("Wishlist found for id: " + wishlistId);
				return new ResponseEntity<>(wishlist, HttpStatus.FOUND);
			} else {
				log.error("Whishlist not found for id: " + wishlistId);
				return new ResponseEntity<>("Wishlist not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting wishlist.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Add new wishlist in the database.
	 * 
	 * @param user
	 * @return HttpStatus with with user object or error message.
	 */
	@PostMapping("/add")
	public ResponseEntity<?> addWishlist(@RequestBody Wishlist wishlist) {
		try {
			var response = wishlistService.addWishlist(wishlist);
			log.info("Wishlist added :" + response);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while creating wishlist.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update wishlist in the database.
	 * 
	 * @param wishlist
	 * @param wishlistId
	 * @return HttpStatus with with wishlist object or error message.
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateWishlist(@RequestBody Wishlist wishlist,
			@PathVariable(name = "id", required = true) Long wishlistId) {

		try {
			var response = wishlistService.updateWishlist(wishlist, wishlistId);
			log.info("Wishlist updated: " + response);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (NullPointerException ne) {
			log.error("Wishlist not found for id: " + wishlistId);
			return new ResponseEntity<>("Wishlist not found!", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while updating wishlist.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update wishlist in the database.
	 * 
	 * @param wishlistId
	 * @return HttpStatus with with wishlist id or error message.
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteWishlist(@PathVariable(name = "id", required = true) Long wishlistId) {

		try {
			var response = wishlistService.deleteWishlist(wishlistId);
			log.info("Wishlist deleted with id: " + response);

			if (response != null)
				return new ResponseEntity<>(response, HttpStatus.OK);
			else {
				log.error("Wishlist not found for id: " + wishlistId);
				return new ResponseEntity<>("Wishlist not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while deleting wishlist.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}

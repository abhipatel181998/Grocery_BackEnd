package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grocery.model.Wishlist;
import com.grocery.repository.WishlistRepository;

@Component
public class WishlistServiceImp implements WishlistService {

	@Autowired
	WishlistRepository wishlistRepository;

	/**
	 * Get all the wishlists.
	 * 
	 * @return List<Wishlist> wishlists list.
	 */
	public List<Wishlist> getAllWishlists() {
		return (List<Wishlist>) wishlistRepository.findAll();
	}

	/**
	 * Get wishlist by wishlist id.
	 * 
	 * @param wishlistId
	 * @return Optional<Wishlist> object.
	 */
	public Optional<Wishlist> getWishlistById(Long wishlistId) {
		return wishlistRepository.findById(wishlistId);
	}

	/**
	 * Create Wishlist.
	 * 
	 * @param wishlist
	 * @return saved Wishlist object.
	 */
	public Wishlist addWishlist(Wishlist wishlist) {
		Optional<Wishlist> wishlistData = wishlistRepository.findByUser(wishlist.getUser());
		if(wishlistData.isPresent()) {
			(wishlistData.get().getItem()).add(wishlist.getItem().get(0));
			return wishlistRepository.save(wishlistData.get());
		}
		return wishlistRepository.save(wishlist);
	}


	/**
	 * Delete wishlist by id.
	 * 
	 * @param wishlistId
	 * @return deleted wishlist's id or null
	 */
	public Object deleteWishlist(Long wishlistId) {
		Optional<Wishlist> wishlistData = wishlistRepository.findById(wishlistId);

		if (wishlistData.isPresent()) {
			wishlistRepository.deleteById(wishlistId);
			return wishlistId;
		}

		return null;
	}
}

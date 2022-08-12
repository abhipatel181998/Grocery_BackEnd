package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grocery.model.Wishlist;

@Service
public interface WishlistService {
	public List<Wishlist> getAllWishlists();

	public Optional<Wishlist> getWishlistById(Long wishlistId);

	public Wishlist addWishlist(Wishlist wishlist);

	public Object deleteWishlist(Long wishlistId);
}

package com.grocery.repository;

import org.springframework.data.repository.CrudRepository;

import com.grocery.model.Wishlist;

public interface WishlistRepository extends CrudRepository<Wishlist, Long> {

}

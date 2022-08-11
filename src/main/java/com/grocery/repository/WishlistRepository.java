package com.grocery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grocery.model.Wishlist;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Long> {

}

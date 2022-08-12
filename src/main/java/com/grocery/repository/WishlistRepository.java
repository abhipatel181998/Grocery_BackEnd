package com.grocery.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grocery.model.User;
import com.grocery.model.Wishlist;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Long> {
	Optional<Wishlist> findByUser(User user);
}

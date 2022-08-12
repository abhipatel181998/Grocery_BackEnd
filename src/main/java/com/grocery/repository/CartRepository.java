package com.grocery.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grocery.model.Cart;
import com.grocery.model.User;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long>{
	Optional<Cart> findByUser(User user);
}

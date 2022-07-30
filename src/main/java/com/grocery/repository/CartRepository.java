package com.grocery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grocery.model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer>{

}

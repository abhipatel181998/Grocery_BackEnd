package com.grocery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grocery.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
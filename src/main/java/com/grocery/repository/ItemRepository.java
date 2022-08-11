package com.grocery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grocery.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item,Long> {

}

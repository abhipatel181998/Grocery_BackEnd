package com.grocery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grocery.model.Orders;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Long> {

}

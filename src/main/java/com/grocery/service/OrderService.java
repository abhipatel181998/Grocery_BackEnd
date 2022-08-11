package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grocery.model.Orders;

@Service
public interface OrderService {
	public List<Orders> getAllOrders();

	public Optional<Orders> getOrderById(Long orderId);

	public Orders addOrder(Orders orders);

	public Orders updateOrder(Orders order, Long orderId);

	public Object deleteOrder(Long orderId);
}

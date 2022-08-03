package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocery.model.Orders;
import com.grocery.repository.OrdersRepository;

@Service
public class OrdersServiceImp {

	@Autowired
	OrdersRepository ordersRepository;

	/**
	 * Get all the orders.
	 * 
	 * @return (List<Orders>  Orders list.
	 */
	public List<Orders> getAllOrders() {
		return (List<Orders>) ordersRepository.findAll();
	}

	/**
	 * Get orders by order id.
	 * 
	 * @param orderId
	 * @return Optional<Orders> object.
	 */
	public Optional<Orders> getOrderById(Long orderId) {
		return ordersRepository.findById(orderId);
	}

	/**
	 * Create Order.
	 * 
	 * @param orders
	 * @return saved Order object.
	 */
	public Orders addOrder(Orders orders) {
		return ordersRepository.save(orders);
	}

	/**
	 * Update order by id.
	 * 
	 * @param order Object of Orders
	 * @param orderId
	 * @return updated Orders object.
	 */
	public Orders updateOrder(Orders order, Long orderId) {
		Optional<Orders> orderData = ordersRepository.findById(orderId);

		if (orderData.isPresent()) {
			return ordersRepository.save(order);
		}

		return null;
	}

	/**
	 * Delete order by id.
	 * 
	 * @param orderId
	 * @return deleted Order's id or null
	 */
	public Object deleteOrder(Long orderId) {
		Optional<Orders> orderData = ordersRepository.findById(orderId);

		if (orderData.isPresent()) {
			ordersRepository.deleteById(orderId);
			return orderId;
		}

		return null;
	}

}

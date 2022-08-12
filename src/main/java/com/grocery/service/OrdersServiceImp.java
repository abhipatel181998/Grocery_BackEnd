package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grocery.model.Orders;
import com.grocery.repository.OrdersRepository;

import static com.grocery.model.Orders.status.NEW;
import static com.grocery.model.Orders.status.APPROVED;
import static com.grocery.model.Orders.status.FULLFILLED;
import static com.grocery.model.Orders.status.REJECTED;;

@Component
public class OrdersServiceImp implements OrderService {

	@Autowired
	OrdersRepository ordersRepository;

	/**
	 * Get all the orders.
	 * 
	 * @return (List<Orders> Orders list.
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
	 * @param order   Object of Orders
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

	public Object updateOrderStatus(String status, Long orderId) {
		if(ordersRepository.findById(orderId).isPresent()) {
			Orders order = ordersRepository.findById(orderId).get();
			System.out.println(status);
			
			if (status.equals("NEW"))
				order.setStatus(NEW);

			if (status.equals("APPROVED"))
				order.setStatus(APPROVED);

			if (status.equals("FULLFILLED"))
				order.setStatus(FULLFILLED);

			if (status.equals("REJECTED"))
				order.setStatus(REJECTED);

			System.out.println(order);
			return ordersRepository.save(order);
		}
		return null;
	}

}

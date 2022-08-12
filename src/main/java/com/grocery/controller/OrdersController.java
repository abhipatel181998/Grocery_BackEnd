package com.grocery.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.model.Orders;
import com.grocery.service.OrderService;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class OrdersController {

	@Autowired
	OrderService ordersService;

	/**
	 * @return all the orders.
	 */
	@GetMapping("/order")
	public ResponseEntity<?> getOrders() {
		try {
			log.info("Get All Orders Accessed.");
			return new ResponseEntity<>(ordersService.getAllOrders(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting orders.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param orderId
	 * @return orders object found by orderId.
	 */
	@GetMapping("/order/{id}")
	public ResponseEntity<?> getOrderById(@PathVariable(name = "id", required = true) Long orderId) {
		try {
			Optional<Orders> order = ordersService.getOrderById(orderId);

			if (order.isPresent()) {
				log.info("order found for id: " + orderId);
				return new ResponseEntity<>(order, HttpStatus.FOUND);
			} else {
				log.error("order not found for id: " + orderId);
				return new ResponseEntity<>("Order not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting order.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Add new order in the database.
	 * 
	 * @param orders
	 * @return HttpStatus with with orders object or error message.
	 */
	@PostMapping("/order/add")
	public ResponseEntity<?> addOrder(@RequestBody Orders orders) {
		try {
			var response = ordersService.addOrder(orders);
			log.info("Order added :" + response);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while creating order.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update order in the database.
	 * 
	 * @param order
	 * @param orderId
	 * @return HttpStatus with with order object or error message.
	 */
	@PutMapping("/order/{id}")
	public ResponseEntity<?> updateOrder(@RequestBody Orders orders,
			@PathVariable(name = "id", required = true) Long orderId) {

		try {
			var response = ordersService.updateOrder(orders, orderId);
			log.info("Order updated: " + response);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (NullPointerException ne) {
			log.error("Order not found for id: " + orderId);
			return new ResponseEntity<>("Order not found!", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while updating order.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/order/updateStatus/{id}")
	public ResponseEntity<?> updateOrderStatus(@RequestBody Status status,
			@PathVariable(name = "id", required = true) Long orderId) {

		try {
			System.out.println(status.getStatus());
			var response = ordersService.updateOrderStatus(status.getStatus(), orderId);
			log.info("Order Status updated: " + response);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (NullPointerException ne) {
			log.error("Order not found for id: " + orderId);
			return new ResponseEntity<>("Order not found!", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while updating order status.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update order in the database.
	 * 
	 * @param orderId
	 * @return HttpStatus with with order id or error message.
	 */
	@DeleteMapping("/order/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable(name = "id", required = true) Long orderId) {

		try {
			var response = ordersService.deleteOrder(orderId);
			log.info("Order deleted with id: " + response);

			if (response != null)
				return new ResponseEntity<>(response, HttpStatus.OK);
			else {
				log.error("Order not found for id: " + orderId);
				return new ResponseEntity<>("Order not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while deleting order.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}

@Data
class Status{
	private String status;
}

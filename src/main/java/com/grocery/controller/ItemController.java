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
import org.springframework.web.bind.annotation.RestController;

import com.grocery.model.Item;
import com.grocery.service.ItemService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class ItemController {
	@Autowired
	ItemService itemService;

	/**
	 * @return all the items.
	 */
	@GetMapping("/item")
	public ResponseEntity<?> getItems() {
		try {
			log.info("Get All Items Accessed.");
			return new ResponseEntity<>(itemService.getAllItem(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting items.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param itemId
	 * @return Item object found by itemId.
	 */
	@GetMapping("/item/{id}")
	public ResponseEntity<?> getItemById(@PathVariable(name = "id", required = true) Long itemId) {
		try {
			Optional<Item> item = itemService.getItemById(itemId);

			if (item.isPresent()) {
				log.info("Item found for id: " + itemId);
				return new ResponseEntity<>(item, HttpStatus.FOUND);
			} else {
				log.error("Item not found for id: " + itemId);
				return new ResponseEntity<>("Item not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting item.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Add new item in the database.
	 * 
	 * @param item
	 * @return HttpStatus with with item object or error message.
	 */
	@PostMapping("/item")
	public ResponseEntity<?> addItem(@RequestBody Item item) {
		try {
			var response = itemService.addItem(item);
			log.info("Item added :" + response);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while creating item.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update item in the database.
	 * 
	 * @param item
	 * @param itemId
	 * @return HttpStatus with with item object or error message.
	 */
	@PutMapping("/item/{id}")
	public ResponseEntity<?> updateItem(@RequestBody Item item,
			@PathVariable(name = "id", required = true) Long itemId) {

		try {
			var response = itemService.updateItem(item, itemId);
			log.info("Item updated: " + response);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (NullPointerException ne) {
			log.error("Item not found for id: " + itemId);
			return new ResponseEntity<>("Item not found!", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while updating item.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update item in the database.
	 * 
	 * @param itemId
	 * @return HttpStatus with with item id or error message.
	 */
	@DeleteMapping("/item/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable(name = "id", required = true) Long itemId) {

		try {
			var response = itemService.deleteItem(itemId);
			log.info("Item deleted with id: " + response);

			if (response != null)
				return new ResponseEntity<>(response, HttpStatus.OK);
			else {
				log.error("Item not found for id: " + itemId);
				return new ResponseEntity<>("Item not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while deleting Item.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}

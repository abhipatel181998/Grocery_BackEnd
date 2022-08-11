package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grocery.model.Item;
import com.grocery.repository.ItemRepository;

@Component
public class ItemServiceImp implements ItemService {

	@Autowired
	ItemRepository itemRepository;

	/**
	 * Get all the item.
	 * 
	 * @return (List<Item> Item list.
	 */
	public List<Item> getAllItem() {
		return (List<Item>) itemRepository.findAll();
	}

	/**
	 * Get item by item id.
	 * 
	 * @param itemId
	 * @return Optional<Item> object.
	 */
	public Optional<Item> getItemById(Long itemId) {
		return itemRepository.findById(itemId);
	}

	/**
	 * Create Item.
	 * 
	 * @param item
	 * @return saved Item object.
	 */
	public Item addItem(Item item) {
		return itemRepository.save(item);
	}

	/**
	 * Update item by id.
	 * 
	 * @param item   Object of Item
	 * @param itemId
	 * @return updated Item object.
	 */
	public Item updateItem(Item item, Long itemId) {
		Optional<Item> itemData = itemRepository.findById(itemId);

		if (itemData.isPresent()) {
			return itemRepository.save(item);
		}

		return null;
	}

	/**
	 * Delete item by id.
	 * 
	 * @param itemId
	 * @return deleted Item id or null
	 */
	public Object deleteItem(Long itemId) {
		Optional<Item> itemData = itemRepository.findById(itemId);

		if (itemData.isPresent()) {
			itemRepository.deleteById(itemId);
			return itemId;
		}

		return null;
	}

}

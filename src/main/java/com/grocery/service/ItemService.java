package com.grocery.service;

import java.util.List;
import java.util.Optional;

import com.grocery.model.Item;



public interface ItemService {
	public List<Item> getAllItem();

	public Optional<Item> getItemById(Long itemId);

	public Item addItem(Item item);

	public Item updateItem(Item item, Long itemId);

	public Object deleteItem(Long itemId);
}

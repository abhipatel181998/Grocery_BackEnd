package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grocery.model.Category;

@Service
public interface CategoryService {
	public List<Category> getAllCategories();

	public Optional<Category> getCategoryById(Long categoryId);

	public Category addCategory(Category category);

	public Category updateCategory(Category category, Long categoryId);

	public Object deleteCategory(Long categoryId);
}

package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grocery.model.Category;
import com.grocery.repository.CategoryRepository;

@Component
public class CategoryServiceImp implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * Get all the categories.
	 * 
	 * @return (List<Category> Category list.
	 */
	public List<Category> getAllCategories() {
		return (List<Category>) categoryRepository.findAll();
	}

	/**
	 * Get orders by category id.
	 * 
	 * @param categoryId
	 * @return Optional<Category> object.
	 */
	public Optional<Category> getCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId);
	}

	/**
	 * Create Category.
	 * 
	 * @param category
	 * @return saved Category object.
	 */
	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

	/**
	 * Update category by id.
	 * 
	 * @param category Object of Category
	 * @param categoryId
	 * @return updated Category object.
	 */
	public Category updateCategory(Category category, Long categoryId) {
		Optional<Category> categoryData = categoryRepository.findById(categoryId);

		if (categoryData.isPresent()) {
			category.setCategoryId(categoryId);
			return categoryRepository.save(category);
		}

		return null;
	}

	/**
	 * Delete category by id.
	 * 
	 * @param categoryId
	 * @return deleted category id or null
	 */
	public Object deleteCategory(Long categoryId) {
		Optional<Category> categoryData = categoryRepository.findById(categoryId);

		if (categoryData.isPresent()) {
			categoryRepository.deleteById(categoryId);
			return categoryId;
		}

		return null;
	}

}

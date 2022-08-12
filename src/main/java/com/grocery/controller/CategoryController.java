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

import com.grocery.model.Category;
import com.grocery.service.CategoryService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	/**
	 * @return all the categories.
	 */
	@GetMapping("/category")
	public ResponseEntity<?> getCategory() {
		try {
			log.info("Get All Categories Accessed.");
			return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting categories.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param categoryId
	 * @return Category object found by categoryId.
	 */
	@GetMapping("/category/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable(name = "id", required = true) Long categoryId) {
		try {
			Optional<Category> category = categoryService.getCategoryById(categoryId);

			if (category.isPresent()) {
				log.info("Category found for id: " + categoryId);
				return new ResponseEntity<>(category, HttpStatus.OK);
			} else {
				log.error("Category not found for id: " + categoryId);
				return new ResponseEntity<>("Category not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting category.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Add new category in the database.
	 * 
	 * @param category
	 * @return HttpStatus with with category object or error message.
	 */
	@PostMapping("/category")
	public ResponseEntity<?> addCategory(@RequestBody Category category) {
		try {
			var response = categoryService.addCategory(category);
			log.info("Category added :" + response);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while creating category.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update category in the database.
	 * 
	 * @param category
	 * @param categoryId
	 * @return HttpStatus with with category object or error message.
	 */
	@PutMapping("/category/{id}")
	public ResponseEntity<?> updatecategory(@RequestBody Category category,
			@PathVariable(name = "id", required = true) Long categoryId) {

		try {
			var response = categoryService.updateCategory(category, categoryId);
			log.info("Category updated: " + response);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (NullPointerException ne) {
			log.error("Category not found for id: " + categoryId);
			return new ResponseEntity<>("Category not found!", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while updating category.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update category in the database.
	 * 
	 * @param categoryId
	 * @return HttpStatus with with category id or error message.
	 */
	@DeleteMapping("/category/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable(name = "id", required = true) Long categoryId) {

		try {
			var response = categoryService.deleteCategory(categoryId);
			log.info("category deleted with id: " + response);

			if (response != null)
				return new ResponseEntity<>(response, HttpStatus.OK);
			else {
				log.error("Category not found for id: " + categoryId);
				return new ResponseEntity<>("Category not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while deleting Category.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}

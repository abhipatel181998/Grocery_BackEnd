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

import com.grocery.model.User;
import com.grocery.service.UserService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("user")
@Log4j2
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * @return all the users.
	 */
	@GetMapping("")
	public ResponseEntity<?> getUsers() {
		try {
			log.info("Get All Users Accessed.");
			return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting users.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param userId
	 * @return User object found by userId.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(name = "id", required = true) int userId) {
		try {
			Optional<User> user = userService.getUserById(userId);

			if (user.isPresent()) {
				log.info("User found for id: " + userId);
				return new ResponseEntity<>(user, HttpStatus.FOUND);
			} else {
				log.error("User not found for id: " + userId);
				return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while getting users.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Add new user in the database.
	 * 
	 * @param user
	 * @return HttpStatus with with user object or error message.
	 */
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
			var response = userService.addUser(user);
			log.info("User added :" + response);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while creating user.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update user in the database.
	 * 
	 * @param user
	 * @param userId
	 * @return HttpStatus with with user object or error message.
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User user,
			@PathVariable(name = "id", required = true) int userId) {

		try {
			var response = userService.updateUser(user, userId);
			log.info("User updated: " + response);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (NullPointerException ne) {
			log.error("User not found for id: " + userId);
			return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while deleting user.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Update user in the database.
	 * 
	 * @param userId
	 * @return HttpStatus with with user id or error message.
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "id", required = true) int userId) {

		try {
			var response = userService.deleteUser(userId);
			log.info("User deleted with id: " + response);

			if (response != null)
				return new ResponseEntity<>(response, HttpStatus.OK);
			else {
				log.error("User not found for id: " + userId);
				return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>("Error occcured while DELETING user.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}

package com.grocery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocery.model.User;
import com.grocery.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * Get all the users.
	 * @return List<User> list.
	 */
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	/**
	 * Get user by user id.
	 * @param userId
	 * @return Optional<User> User object or null.
	 */
	public Optional<User> getUserById(int userId) {
		return userRepository.findById(userId);
	}

	/**
	 * Create new user.
	 * 
	 * @param user
	 * @return saved User object
	 */
	public User addUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * Update user by id.
	 * 
	 * @param user
	 * @param userId
	 * @return updated User object.
	 */
	public User updateUser(User user, int userId) {
		Optional<User> userData = userRepository.findById(userId);

		if (userData.isPresent()) {
			return userRepository.save(user);
		}

		return null;
	}

	/**
	 * Delete user by id.
	 * 
	 * @param userId
	 * @return deleted User's id or null
	 */
	public Object deleteUser(int userId) {
		Optional<User> userData = userRepository.findById(userId);

		if (userData.isPresent()) {
			userRepository.deleteById(userId);
			return userId;
		}

		return null;
	}

}

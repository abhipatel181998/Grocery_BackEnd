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
	 * @return List<User> list.
	 */
	public List<User> getAll(){
		return (List<User>) userRepository.findAll();
	}
	
	/**
	 * @param userId
	 * @return Optional<User> User object or null.
	 */
	public Optional<User> getById(int userId) {
		return userRepository.findById(userId);
	}

	/**
	 * Create new user.
	 * 
	 * @param user
	 * @return saved User object
	 */
	public User add(User user) {
		return userRepository.save(user);
	}

	/**
	 * Update user by id.
	 * 
	 * @param user
	 * @param userId
	 * @return updated User object.
	 */
	public User update(User user, int userId) {
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
	public Object delete(int userId) {
		Optional<User> userData = userRepository.findById(userId);

		if (userData.isPresent()) {
			userRepository.deleteById(userId);
			return userId;
		}

		return null;
	}

}

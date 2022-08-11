package com.grocery.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.grocery.model.Role;
import com.grocery.model.User;
import com.grocery.repository.RoleRepository;
import com.grocery.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserService, UserDetailsService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final RoleRepository roleRepository;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			log.info("User not found for username {}", username);
			throw new UsernameNotFoundException("User not found for username: " + username);
		} else {
			log.info("User found for username: " + username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	/**
	 * Get all the users.
	 * 
	 * @return List<User> list.
	 */
	public List<User> getAllUsers() {
		log.info("Getting all the users.");
		return (List<User>) userRepository.findAll();
	}

	/**
	 * Get user by user id.
	 * 
	 * @param userId
	 * @return Optional<User> User object or null.
	 */
	public Optional<User> getUserById(Long userId) {
		log.info("Getting user details for user id: {}", userId);
		return userRepository.findById(userId);
	}

	public User getUserByEmail(String email) {
		log.info("Getting user details for emial: {}", email);
		return userRepository.findByEmail(email);
	}

	/**
	 * Create new user.
	 * 
	 * @param user
	 * @return saved User object
	 */
	public User saveUser(User user) {
		log.info("Saving user with user name: {}", user.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	/**
	 * Update user by id.
	 * 
	 * @param user
	 * @param userId
	 * @return updated User object.
	 */
	public User updateUser(User user, Long userId) {
		Optional<User> userData = userRepository.findById(userId);

		if (userData.isPresent()) {
			log.info("Updating user with user id: {}", userId);
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
	public Object deleteUser(Long userId) {
		Optional<User> userData = userRepository.findById(userId);

		if (userData.isPresent()) {
			log.info("Deleting user with user id: {}", userId);
			userRepository.deleteById(userId);
			return userId;
		}

		return null;
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving Role: {}", role.getName());
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String email, String roleName) {
		User user = userRepository.findByEmail(email);
		Role role = roleRepository.findByName(roleName);

		log.info("Adding role {} to username {}", roleName, email);
		user.getRoles().add(role);
	}

}

package com.grocery.service;

import java.util.List;
import java.util.Optional;

import com.grocery.model.Role;
import com.grocery.model.User;

public interface UserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addRoleToUser(String email, String roleName);
	Optional<User> getUserById(Long userId);
	List<User>getAllUsers();
}

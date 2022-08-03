package com.grocery.repository;

import org.springframework.data.repository.CrudRepository;

import com.grocery.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByName(String name);
}

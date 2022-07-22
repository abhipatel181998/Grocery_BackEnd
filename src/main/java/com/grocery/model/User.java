package com.grocery.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class User {
	
	public enum Gender{
		MALE, FEMELE
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id")
	private String userId;

	@Column(nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private Gender gender;
	
	@Column (nullable = false)
	private int isAdmin = 0;
	
	@Column (nullable = false, length = 10)
	private long phone;
	
	private String photo;
	
	@OneToMany(mappedBy = "user")
	private List<Orders> orders;
}
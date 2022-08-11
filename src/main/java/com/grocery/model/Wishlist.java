package com.grocery.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Wishlist {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "wishlist_id")
	private Long wishlistId;
	
	@OneToOne
	private User user;
	
	@OneToMany
	private List<Item> item;
}

package com.grocery.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "item_id")
	private int itemId;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = false)
	private double price;
	private String photo;

	private float discount;
	private Date offerValidTill;

	@Column(nullable = false)
	private int quantity = 0;

	private String description;

	@ManyToOne
	private Category category;

}

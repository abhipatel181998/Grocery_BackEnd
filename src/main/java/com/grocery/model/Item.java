package com.grocery.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "item_id")
	private Long itemId;
	
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Category category;

}

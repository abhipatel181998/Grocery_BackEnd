package com.grocery.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

	public static enum status {
		NEW, APPROVED, FULLFILLED, REJECTED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "order_id")
	private Long orderId;

	@Column(nullable = false)
	private String address;

	@Column(name = "order_date", nullable = false)
	private Date orderDate;

	@Column(nullable = false)
	private double total = 0;

	@Column(nullable = false)
	private status status = this.status.NEW;

	@ManyToOne
	private User user;

	@OneToMany
	private List<Item> items;

}

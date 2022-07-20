package com.grocery.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Order {
	
	public enum status{
		NEW, PENDING, INPROGRESS, FINISHED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "order_id")
	private int orderId;
	
	@Column(nullable = false)
	private String address;
	
	@Column(name = "order_date" ,nullable = false)
	private Date orderDate;
	
	@Column(nullable = false)
	private status status = this.status.NEW;
	
}

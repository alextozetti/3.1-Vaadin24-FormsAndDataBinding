package com.vaadin.training.forms.exercises.ex2;

import java.time.LocalDate;

public class Product {

	private String name;
	private double price;
	private LocalDate available;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getAvailable() {
		return available;
	}

	public void setAvailable(LocalDate available) {
		this.available = available;
	}
}

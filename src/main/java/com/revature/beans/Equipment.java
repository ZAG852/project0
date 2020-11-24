package com.revature.beans;

public class Equipment {
	private float price = 0.0f;
	private String name = "";
	private int id = -1;
	private Boolean isFood = false;
	public Equipment(float price, String name, Boolean isFood)
	{
		this.setPrice(price);
		this.setName(name);
		this.setIsFood(isFood);
	}
	float getPrice() {
		return price;
	}
	void setPrice(float price) {
		this.price = price;
	}
	String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	int getId() {
		return id;
	}
	void setId(int id) {
		this.id = id;
	}
	Boolean getIsFood() {
		return isFood;
	}
	void setIsFood(Boolean isFood) {
		this.isFood = isFood;
	}
}

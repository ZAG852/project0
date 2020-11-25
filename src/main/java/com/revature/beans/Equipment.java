package com.revature.beans;

public class Equipment {
	private float price = 0.0f;
	private String name = "";
	private int id = -1;
	private Boolean isFood = false;
	private int quantity = 1;
	public Equipment(float price, String name, Boolean isFood, int id, int quantity)
	{
		this.setPrice(price);
		this.setName(name);
		this.setIsFood(isFood);
		this.id = id;
		this.setQuantity(quantity);
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getIsFood() {
		return isFood;
	}
	public void setIsFood(Boolean isFood) {
		this.isFood = isFood;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

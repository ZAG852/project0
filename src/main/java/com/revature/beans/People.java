package com.revature.beans;

public class People {
	private int id;
	private String username;
	private String password;
	private boolean isAdmin;
	public People(String username, String Password, int id, boolean isAdmin)
	{
		this.id = id;
		this.username = username;
		this.password = Password;
		this.isAdmin = isAdmin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	

}

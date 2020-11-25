package com.revature.beans;

public class Trip {
	private int tripId;
	private String tripName;
	public Trip(int id, String name) {
		this.tripId = id;
		this.tripName = name;
	}
	public Trip(String name) {
		this.tripName = name;
	}
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
}

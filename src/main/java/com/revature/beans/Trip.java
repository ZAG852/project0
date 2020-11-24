package com.revature.beans;

public class Trip {
	private int tripId;
	private String tripName;
	String getTripName() {
		return tripName;
	}
	void setTripName(String tripName) {
		this.tripName = tripName;
	}
	int getTripId() {
		return tripId;
	}
	void setTripId(int tripId) {
		this.tripId = tripId;
	}
}

package com.revature.service;

import java.util.ArrayList;

import com.revature.beans.Equipment;
import com.revature.beans.Trip;
import com.revature.dao.TripDAO;

public class TripManager {
	TripDAO trip = new TripDAO();
	
	public Trip createTrip(int userId, String name) {
		return trip.createTrip(userId, name);
	}
	public boolean deleteTrip(int tripId)
	{
		return trip.deleteTrip(tripId);
	}
	ArrayList<Equipment> GetItemsTrip(int tripId){
		return trip.SelectEquipmentTrip(tripId);
	}
	ArrayList<Equipment> GetItemsFood(int tripId)
	{
		return trip.SelectFoodTrip(tripId);
	}
}

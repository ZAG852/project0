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
	public ArrayList<Equipment> GetItemsTrip(int tripId){
		return trip.SelectEquipmentTrip(tripId);
	}
	public ArrayList<Equipment> GetItemsFood(int tripId)
	{
		return trip.SelectFoodTrip(tripId);
	}
	public Trip getTrip(int tripId, int userId) {
		return trip.GetATrip(tripId, userId);
	}
	public boolean addItemToTrip(int userId, int tripId, int equipId, int quantity)
	{
		return trip.AddItemToTrip(tripId, userId, quantity, equipId);
	}
	public Trip EditTripName(int userId, int tripId, String newName) {
		return trip.updateTripName(userId, tripId, newName);
	}
}

package com.revature.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.beans.Equipment;
import com.revature.beans.Trip;
public class TripDAO {
	DatabaseManager db = new DatabaseManager();
	
	
	public ArrayList<Trip> getAllTrips(int userId){
		ArrayList<Trip> trips = new ArrayList<Trip>();
		String sqlQuery = "Select * from Trip"
				+ "where userId = ?;";
		try (Connection connection = db.getLocalConnection("OutdoorApp", "postgres", "myLocal")) {
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery(sqlQuery);
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				Trip trip = new Trip(id, name);
				trips.add(trip);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return trips;
	}
	public Trip createTrip(int userId, String name) {
		int autoId =0;
		String sqlQuery = "insert into Trip"
				+ "(userId, tripName)"
				+ "VALUES"
				+ "(?,?)";
		try (Connection connection = db.getLocalConnection("OutdoorApp", "postgres", "myLocal")){
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery,Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, userId);
			pstmt.setString(2, name);
			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting new user failed, no rows were affected");
			}
			autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Creating user failed, no ID generated");
			}
			
			connection.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return new Trip(autoId, name);
	}
	public ArrayList<Equipment> SelectEquipmentTrip(int tripId) {
		ArrayList<Equipment> items = new ArrayList<Equipment>();
		String sqlQuery = "Select eq.equipname, eq.price, et.quantity, eq.equipid"
				+ "from equipmentTrip et "
				+ "INNER JOIN equipment eq "
				+ "ON et.equipId = eq.equipId"
				+ "where et.tripId = ? AND eq.isFood = false;";
		
		try(Connection connection = db.getLocalConnection("OutdoorApp", "postgres", "myLocal")){
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setInt(1,tripId);
			ResultSet rs = pstmt.executeQuery(sqlQuery);
			while (rs.next()) {
				String name = rs.getString(1);
				float price = rs.getFloat(2);
				int quantity = rs.getInt(3);
				int id = rs.getInt(4);
				Equipment item = new Equipment(price,name, false, id, quantity);
				items.add(item);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	public ArrayList<Equipment> SelectFoodTrip(int tripId) {
		ArrayList<Equipment> items = new ArrayList<Equipment>();
		String sqlQuery =  "Select eq.equipname, eq.price, et.quantity, eq.equipid "
				+ "from equipmentTrip et "
				+ "INNER JOIN equipment eq "
				+ "ON et.equipId = eq.equipId"
				+ "where et.tripId = ? AND eq.isFood = true;";
		
		try(Connection connection = db.getLocalConnection("OutdoorApp", "postgres", "myLocal")){
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1,tripId);
			ResultSet rs = pstmt.executeQuery(sqlQuery);
			while (rs.next()) {
				String name = rs.getString(1);
				float price = rs.getFloat(2);
				int quantity = rs.getInt(3);
				int id = rs.getInt(4);
				Equipment item = new Equipment(price,name, false, id, quantity);
				items.add(item);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	public boolean clearItems(int tripId) {
		String sqlQuery = "Delete from equipmentTrip where tripId = ?";
		try(Connection connection = db.getLocalConnection("OutdoorApp", "postgres", "myLocal")){
		PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
		pstmt.setInt(1, tripId);
		return pstmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteTrip(int tripId)
	{
		clearItems(tripId);
		String sqlQuery = "Delete from Trip where tripId = ?";
		try(Connection connection = db.getLocalConnection("OutdoorApp", "postgres", "myLocal")){
		PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
		pstmt.setInt(1, tripId);
		return pstmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteItem(int tripId, int equipId) {
		String sqlQuery = "Select et.quantity"
				+ "from equipmentTrip et "
				+ "where et.tripId = ? AND eq.equipId = ?;";
		try(Connection connection = db.getLocalConnection("OutdoorApp", "postgres", "myLocal")){
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setInt(1,tripId);
			pstmt.setInt(2, equipId);
			ResultSet rs = pstmt.executeQuery(sqlQuery);
			int quantity = rs.getInt(1);
			if(quantity > 1)
			{
				quantity--;
				return true;
			}else {
				pstmt.close();
				String sql = "DELETE"
						+ "from equipmentTrip et"
						+ "where et.tripId = ? and et.equipId = ?;";
				pstmt = connection.prepareStatement(sql);
				return pstmt.execute();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

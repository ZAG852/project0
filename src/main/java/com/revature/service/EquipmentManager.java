package com.revature.service;

import java.util.ArrayList;
import java.util.Hashtable;

import com.revature.beans.gear.Equipment;

public class EquipmentManager {
	ArrayList<Equipment> allEquipment;
	Hashtable<Equipment, Integer> userEquipment;
	
	public EquipmentManager() {
		allEquipment = new ArrayList<Equipment>();
		userEquipment = new Hashtable<Equipment, Integer>();
	}
	
	public ArrayList<Equipment> getAllEquipment(){
		return null;
	}
	public void addUserEquipment(Equipment e) {
		if(userEquipment.containsValue(e)) {
			Integer i = userEquipment.get(e);
			i++;
			userEquipment.replace(e, i);
		}
	}
}

package com.revature.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Equipment;
import com.revature.beans.People;
import com.revature.beans.Trip;
import com.revature.beans.UserTemplate;
import com.revature.dao.TripDAO;

public class GetTripServlet {

	String message = "";
	TripDAO trip = new TripDAO();
	
	private ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		 message = "Please Login!";
	}
	
	public GetTripServlet() {
		// TODO Auto-generated constructor stub
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.isNew())
		{
			response.getWriter().append("Please login");
		}
		else {
			People p = (People) session.getAttribute("user");
			ArrayList<Trip> trips = trip.getAllTrips(p.getId());
			String jsonString = "";
			for(Trip t : trips)
			{
				jsonString = objectMapper.writeValueAsString(t);
				response.getWriter().append(jsonString);
			}
		}
				
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = request.getReader();
		HttpSession session = request.getSession(true);
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		String jsonString = sb.toString();
		//UserTemplate userData = objectMapper.readValue(jsonString, UserTemplate.class);
		//if(p != null)
		//{
			//if(session.isNew())
			//{
			//	session.setAttribute("trip", p);
			//}
		//	String insertedUserJSON = objectMapper.writeValueAsString();
			
			//response.getWriter().append(insertedUserJSON);
			response.setContentType("application/json");
			response.setStatus(201);
		//}
	}
}

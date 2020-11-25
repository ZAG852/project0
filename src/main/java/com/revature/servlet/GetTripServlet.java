package com.revature.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Equipment;
import com.revature.beans.People;
import com.revature.beans.Trip;
import com.revature.beans.TripTemplate;
import com.revature.beans.UserTemplate;
import com.revature.dao.TripDAO;
import com.revature.service.TripManager;

public class GetTripServlet extends HttpServlet {

	String message = "";
	TripDAO trip = new TripDAO();
	TripManager TM = new TripManager();
	
	private ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		 message = "Please Login!";
	}
	
	public GetTripServlet() {
		// TODO Auto-generated constructor stub
		super();
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
		System.out.println(jsonString);
		TripTemplate tripData = objectMapper.readValue(jsonString, TripTemplate.class);
		People person = (People)session.getAttribute("user");
		System.out.println(person.getId());
		Trip t = TM.getTrip(tripData.getId(), person.getId());
		if(t != null)
		{
			session.setAttribute("trip", t);
			String insertedTripJSON = objectMapper.writeValueAsString(t);
			System.out.println(insertedTripJSON);
			response.getWriter().append(insertedTripJSON);
			response.setContentType("application/json");
			response.setStatus(201);
		}
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		if(session.getAttribute("user") != null)
		{
			if(session.getAttribute("trip")!= null) {
				BufferedReader reader = request.getReader();
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				People p = (People)session.getAttribute("user");
				Trip t = (Trip)session.getAttribute("trip");
				String jsonString = sb.toString();
				System.out.println(jsonString);
				TripTemplate tripData = objectMapper.readValue(jsonString, TripTemplate.class);
				Trip newTrip = TM.EditTripName(p.getId(), t.getTripId(), tripData.getName());
				if(newTrip != null)
				{
					response.getWriter().append(objectMapper.writeValueAsString(newTrip));
					session.setAttribute("trip", newTrip);
					response.setContentType("application/json");
					response.setStatus(200);
				}
			}
			else {
				response.getWriter().append("Select a trip");
			}
		}
		else {
			response.getWriter().append("Please login!");
		}
	}
}

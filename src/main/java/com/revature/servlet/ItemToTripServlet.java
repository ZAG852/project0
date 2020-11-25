package com.revature.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Equipment;
import com.revature.beans.People;
import com.revature.beans.Trip;
import com.revature.beans.TripTemplate;
import com.revature.dao.ItemTemplate;
import com.revature.service.TripManager;

/**
 * Servlet implementation class ItemToTripServlet
 */
public class ItemToTripServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TripManager TM = new TripManager();
	private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemToTripServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		HttpSession session = request.getSession(true);
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		String jsonString = sb.toString();
		if(session.getAttribute("user") != null)
		{
			if(session.getAttribute("trip") != null)
			{
				People person = (People)session.getAttribute("user");
				Trip trip = (Trip)session.getAttribute("trip");
				ItemTemplate itemData = objectMapper.readValue(jsonString, ItemTemplate.class);
				boolean e = TM.addItemToTrip(person.getId(), trip.getTripId(), itemData.getId(), itemData.getQuantity());
				response.getWriter().append("The out put is " + e);
			}else {
				response.getWriter().append("Select a trip to assign items to!");
			}
		}else {
			response.getWriter().append("Login to use this feature!");
		}
	}

}

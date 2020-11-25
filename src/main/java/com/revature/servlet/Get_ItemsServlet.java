package com.revature.servlet;

import java.io.IOException;
import java.util.ArrayList;

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
import com.revature.dao.TripDAO;
import com.revature.service.ItemManager;

/**
 * Servlet implementation class Get_ItemsServlet
 */
@WebServlet("/Get_ItemsServlet")
public class Get_ItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper objectMapper = new ObjectMapper();
	ItemManager IM = new ItemManager();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Get_ItemsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.isNew())
		{
			response.getWriter().append("Please login");
		}
		else {
			
			ArrayList<Equipment> items = IM.getAllItems();
			String jsonString = "";
			for(Equipment t : items)
			{
				jsonString = objectMapper.writeValueAsString(t);
				response.getWriter().append(jsonString);
			}
			response.setContentType("application/json");
			response.setStatus(200);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package com.revature.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.UserTemplate;
import com.revature.service.LoginManager;

/**
 * Servlet implementation class Login
 */

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginManager log = new LoginManager();
	private ObjectMapper objectMapper = new ObjectMapper();
	private String message;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		 message = "Please Login!";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set response content type
        //response.setContentType("text/html");
        // Actual logic goes here.
        //PrintWriter out = response.getWriter();
        //out.println("<html>");
        //out.println("<body>");
        //out.println("<link rel=\"stylesheet\" href=\"./resources/com/revature/util/CSS/Style.css\">");
        //out.println("<h1>" + message + "</h1>");
        //out.println("<input type='text' value='Username'/>");
        //out.println("</body>");
        //out.println("</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = request.getReader();
		
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		String jsonString = sb.toString();
		UserTemplate userData = objectMapper.readValue(jsonString, UserTemplate.class);
		System.out.println(log.logUser(userData.getUsername(), userData.getPassword()));
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

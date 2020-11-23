package com.revature.servlet;
// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Extend HttpServlet class
//@WebServlet("/HelloWorld")
public class HelloServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "Hello World";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");
        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<button >Username</button>");
        out.println("</body>");
        out.println("</html>");
    }

    public void destroy() {
        // do nothing.
    }
}

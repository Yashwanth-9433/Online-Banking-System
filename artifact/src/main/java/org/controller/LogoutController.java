package org.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LogoutCont")

public class LogoutController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false); // false = don't create if doesn't exist
		String name=(String) session.getAttribute("name");
	    if (session != null) {
	        session.invalidate();
	    }
	    PrintWriter out=response.getWriter();
 	   	response.setContentType("text/html");
	    out.println("<html><body>"
		   		+ "<p style='font-size: 18px; color: green; font-weight: bold; margin-top: 20px;'>"
		   		+ "Thankyou for using our services " + name + "!!" + "</p>"
		   		+ "<a href='index.html'> Home Page</a></li>"
		   		+ "</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}

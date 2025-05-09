package org.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.jdbc.Accounts;
import org.jdbc.AccountsDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet("/ProfileCont")
public class ProfileController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setDateHeader("Expires", 0); // Proxies

	    // Check if session exists
	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("acctno") == null) {
	        response.sendRedirect("index.html");
	        return;
	    }
	    
		PrintWriter out=response.getWriter();
 	   	response.setContentType("text/html");
 	   	
 	   	ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
	   	AccountsDAO dao=(AccountsDAO) ctx.getBean("AccountsDAO");
	   	
 	   	
 	  out.println("<!DOCTYPE html>");
 	  out.println("<html lang='en'>");
 	  out.println("<head>");
 	  out.println("    <meta charset='UTF-8'>");
 	  out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
 	  out.println("    <title>Profile| Savio Bank</title>");
 	  out.println("    <link rel='stylesheet' href='style.css'>");
 	  out.println("    <link rel='icon' type='image/x-icon' href='savio.png'>");
 	  out.println("</head>");
 	  out.println("<body>");
 	  out.println("    <div id='nav'>");
 	  out.println("        <img src='savio.png' id='photo'>");
 	  out.println("        <nav class='nav'>");
 	  out.println("            <ul>");
 	  out.println("                <li><a href='ProfileCont'> PROFILE</a></li>");
 	  out.println("                <li><a href='BalCont'>CHECK BALANCE</a></li>");
 	  out.println("                <li><a href='DepositCont'>DEPOSIT</a></li>");
 	  out.println("                <li><a href='WithdrawCont'>WITHDRAW</a></li>");
 	  out.println("                <li><a href='TransferController'>TRANSFER</a></li>");
 	 out.println("                <li><a href='TransactionController'>TRANSACTION</a></li>");
 	  out.println("                <li><a href='LogoutCont'>LOGOUT</a></li>");
 	  out.println("            </ul>");
 	  out.println("        </nav>");
 	  out.println("    </div>");
 	  
	  
 	  out.println("<p style='color: blue; font-size: 18px; font-family: Arial;'>");
 	  out.println("Hello, welcome back " + session.getAttribute("name") +"! </p>");
 	  
 	  out.println("    <div class='login-container'>");
 	  out.println("        <p> Welcome " + session.getAttribute("name") + "! Enter Credentials...</p>");
 	  out.println("        <h2>Change Password!!</h2>");
 	  out.println("        <form action='PswdCont' method='POST'>");
 	  out.println("            <input type='password' name='oldpswd' placeholder='Old pswd' required>");
 	  out.println("            <br>");
 	  out.println("            <input type='password' name='newpswd1' placeholder='New pswd' required>");
 	  out.println("            <br>");
 	  out.println("            <input type='password' name='newpswd2' placeholder='Confirm new pswd' required>");
 	  out.println("            <br>");
 	  out.println("            <button type='submit'>Update</button>");
 	  out.println("        </form>");
 	  out.println("    </div>");
 	  out.println("    <div class='login-container'>");
 	  out.println("        <p> Welcome " + session.getAttribute("name") + "! Enter Credentials...</p>");
 	  out.println("        <h2>Close Account!!</h2>");
 	  out.println("        <form action='AcctCont' method='POST'>");
 	  out.println("            <input type='password' name='pswd' placeholder='pswd' required>");
 	  out.println("            <br>");
 	  out.println("            <input type='text' name='confirm' placeholder='type: confirm' required>");
 	  out.println("            <br>");
 	  out.println("            <button type='submit'>Close</button>");
 	  out.println("        </form>");
 	  out.println("    </div>");
 	  out.println("</body>");
 	  out.println("</html>");
 	 session.setMaxInactiveInterval(300);
 	   	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package org.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jdbc.AccountsDAO;
import org.jdbc.TransactionsDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet("/TransferCont")

public class TransferCont extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out=response.getWriter();
 	   	response.setContentType("text/html");
 	   	
 	   	ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
	   	AccountsDAO dao=(AccountsDAO) ctx.getBean("AccountsDAO");
	   	TransactionsDAO dao1=(TransactionsDAO) ctx.getBean("TransactionsDAO");
	   	
	   	HttpSession session = request.getSession();
	   	Integer acctobj= (Integer) session.getAttribute("acctno");
 	   	int acctno=(int)(acctobj);
 	   	String acct=request.getParameter("toacctno");
 	   	int toacctno=Integer.parseInt(acct);
 	   	String pswd=request.getParameter("pswd");
 	   	String amount=request.getParameter("amount");
 	   	float amt=Float.parseFloat(amount);
 	   	
	   	String res=dao.transfer(acctno, pswd, toacctno, amt);
	   	if (res=="Wrong Acct_no and/or Password") {
	   		out.println("<html><body><script>alert('Shegin is always right you are wrong')"
    		   		+ "</script>"
    		   		+ "<p style='font-size: 18px; color: green; font-weight: bold; margin-top: 20px;'>"
    		   		+ "Wrong Password" +"\n" + "</p><a href='TransferController'>  Try Again </a></li></body></html>");
	   	}
   		else if (res=="Not enough balance in account") {
   			
   			float bal=dao.balance(acctno);
   			String formatted = String.format("%.2f", bal);
   			out.println("<html><body><script>alert('"+res+"')"
    		   		+ "</script>"
    		   		+ "<p style='font-size: 18px; color: green; font-weight: bold; margin-top: 20px;'>"
    		   		+ "    Your current balance is: ₹" + formatted + "</p>"
    		   		+ "<a href='TransferController'> Go Back</a></li></body></html>");
   		}
   		else {
   			String name1=dao.getName(acctno);
   			String name=dao.getName(toacctno);
   			dao1.entry(acctno, name1, toacctno, name, pswd, amt);
   			String formatted = String.format("%.2f", amt);
   			out.println("<html><body><script>alert('"+res+"')"
    		   		+ "</script>"
    		   		+ "<p style='font-size: 18px; color: green; font-weight: bold; margin-top: 20px;'>"
    		   		+ "₹" + formatted + " has been transferred to " + name + " successfully!!" + "\n" + "</p>"
    		   		+ "<a href='ProfileCont'> Go Back</a></li></body></html>");
   		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

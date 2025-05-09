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

@WebServlet("/WithCont")
public class WithCont extends HttpServlet{
	
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
 	   	String pswd=request.getParameter("pswd");
 	   	String amount=request.getParameter("amount");
 	   	float amt=Float.parseFloat(amount);
 	   	String name=(String) session.getAttribute("name");
 	   	
	   	if (dao.check(acctno, pswd)) {
	   		String res=dao.withdraw(acctno, pswd, amt);
	   		if (res=="Not enough balance in account") {
	   			
	   			float bal=dao.balance(acctno);
	   			String formatted = String.format("%.2f", bal);
	   			out.println("<html><body><script>alert('"+res+"')"
	    		   		+ "</script>"
	    		   		+ "<p style='font-size: 18px; color: green; font-weight: bold; margin-top: 20px;'>"
	    		   		+ "    Your current balance is: ₹" + formatted + "</p>"
	    		   		+ "<a href='WithdrawCont'> Go Back</a></li></body></html>");
	   		}
	   		else {
	   			dao1.entry(acctno, name, 0, "Withdraw", pswd, amt);
	   			out.println("<html><body><script>alert('"+res+"')"
	    		   		+ "</script><a href='ProfileCont'> Go Back</a></li></body></html>");
	   		}
	   	}
	   	else {
	   		out.println("<html><body><script>alert('Shegin is always right you are wrong')"
    		   		+ "</script><a href='WithdrawCont'> Try Again</a></li></body></html>");
	   	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

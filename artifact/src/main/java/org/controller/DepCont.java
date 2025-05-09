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

@WebServlet("/DepCont")
public class DepCont extends HttpServlet{
	
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
	   		String res=dao.deposit(acctno, pswd, amt);
	   		dao1.entry(0, "Deposit", acctno, name, pswd, amt);
	   		out.println("<html><body><script>alert('"+res+"')"
    		   		+ "</script><a href='ProfileCont'> Go Back</a></li></body></html>");
	   		session.setMaxInactiveInterval(300); //5 mins
	   	}
	   	else {
	   		out.println("<html><body><script>alert('Shegin is always right you are wrong')"
    		   		+ "</script><a href='DepositCont'> Try Again</a></li></body></html>");
	   	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

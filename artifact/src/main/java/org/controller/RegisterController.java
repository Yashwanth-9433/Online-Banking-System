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
import org.jdbc.TransactionsDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet("/RegCont")

public class RegisterController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out=response.getWriter();
 	   	response.setContentType("text/html");
 	   	
 	   	ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
	   	AccountsDAO dao=(AccountsDAO) ctx.getBean("AccountsDAO");
	   	TransactionsDAO dao1=(TransactionsDAO) ctx.getBean("TransactionsDAO");
	   	
 	   	
 	   	String acctno_str=request.getParameter("acctno");
	   	int acctno=Integer.parseInt(acctno_str);
	   	String pswd1=request.getParameter("pswd1");
	   	String pswd2=request.getParameter("pswd2");
	   	float amount=Float.parseFloat(request.getParameter("deposit"));
	   	if (pswd1.equals(pswd2)) {
	   		Accounts acct=new Accounts();
	   		acct.setId(acctno);
	   		acct.setName(request.getParameter("name"));
	   		acct.setEmail(request.getParameter("email"));
	   		acct.setPassword(pswd1);
	   		acct.setAmount(amount);
	   		dao.register(acct);
	   		dao1.entry(0, "Deposit", acctno, acct.getName(), pswd1, amount);
	   		
	   		HttpSession session=request.getSession();
	   		session.setAttribute("acctno",  acctno);
	   		session.setAttribute("password", pswd1);
	   		session.setAttribute("name", acct.getName());
	   		session.setAttribute("amount", amount);
	   		session.setMaxInactiveInterval(300); //5 mins
	   		
	   		response.sendRedirect("ProfileCont");
	   	}
	   	else {
	   		out.println("<html><body><script>alert('Shegin is always right you are wrong')"
    		   		+ "</script><a href='register.html'> Try Again</a></li></body></html>");
	   	}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}

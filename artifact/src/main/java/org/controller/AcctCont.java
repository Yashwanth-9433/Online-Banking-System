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

@WebServlet("/AcctCont")

public class AcctCont extends HttpServlet{
	
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
	   	TransactionsDAO dao1=(TransactionsDAO) ctx.getBean("TransactionsDAO");
	   	
	   	int acctno=(int) session.getAttribute("acctno");
 	    String pswd=request.getParameter("pswd");
 	    String name=(String) session.getAttribute("name");
 	    String confirm=request.getParameter("confirm");
	   	if (dao.check(acctno, pswd) && confirm.equals("confirm")) {
	   		float amount=dao.close(acctno);
	   		String formatted = String.format("%.2f", amount);
	   		dao1.entry(acctno, name, 0, "Withdraw(closed)", pswd, amount);
	   		String res="Balance amount: ₹" + formatted + " succesfull withdrawn";
	   		out.println("<html><body><script>alert('"+res+"')"
    		   		+ "</script><a href='index.html'> Go Back</a></li></body></html>");
	   		session.setMaxInactiveInterval(300); //5 mins
	   		
	   	}
	   	else {
	   		out.println("<html><body><script>alert('Shegin is always right you are wrong')"
    		   		+ "</script><a href='ProfileCont'> Try Again</a></li></body></html>");
	   	}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
}

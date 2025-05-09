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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet("/LoginCont")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		PrintWriter out=response.getWriter();
 	   	response.setContentType("text/html");
 	   	
 	   	ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
	   	AccountsDAO dao=(AccountsDAO) ctx.getBean("AccountsDAO");
	   	
 	   	String acctno_str=request.getParameter("acctno");
 	   	int acctno=Integer.parseInt(acctno_str);
 	   	String password=request.getParameter("password");
 	   	String name=dao.getName(acctno);
 	   	float amount=dao.balance(acctno);
 	   	
	   	if (dao.check(acctno, password)) {
	   		HttpSession session=request.getSession();
	   		session.setAttribute("acctno",  acctno);
	   		session.setAttribute("password", password);
	   		session.setAttribute("name", name);
	   		session.setAttribute("amount", amount);
	   		session.setMaxInactiveInterval(300); //5 mins
	   		
	   		response.sendRedirect("ProfileCont");
	   	}
	   	else {
	   		out.println("<html><body><script>alert('Shegin is always right you are wrong')"
    		   		+ "</script><a href='index.html'> Try Again</a></li></body></html>");
	   	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

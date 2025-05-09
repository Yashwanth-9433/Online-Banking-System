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

@WebServlet("/PswdCont")
public class PasswordController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
	   	
 	   	String oldpswd=request.getParameter("oldpswd");
 	    String newpswd1=request.getParameter("newpswd1");
 	    String newpswd2=request.getParameter("newpswd2");
 	    Integer acctobj= (Integer) session.getAttribute("acctno");
 	   	int acctno=(int)(acctobj);
	   	if (dao.check(acctno, oldpswd) && newpswd1.equals(newpswd2)) {
	   		String res=dao.update_pswd(acctno, oldpswd, newpswd1);
	   		out.println("<html><body><script>alert('"+res+"')"
    		   		+ "</script><a href='ProfileCont'> Go Back</a></li></body></html>");
	   		session.setMaxInactiveInterval(300); //5 mins
	   		
	   	}
	   	else {
	   		out.println("<html><body><script>alert('Shegin is always right you are wrong')"
    		   		+ "</script><a href='ProfileCont'> Try Again</a></li></body></html>");
	   	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

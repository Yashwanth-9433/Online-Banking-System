package org.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.jdbc.Accounts;
import org.jdbc.AccountsDAO;
import org.jdbc.Transactions;
import org.jdbc.TransactionsDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet("/TransactionCont")

public class TransactionCont extends HttpServlet{
	
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
 	   	
	   	List<Map<String, Object>> res=dao1.retrieve(acctno, pswd);
	   	if (res==null) {
	   		out.println("<html><body><script>alert('Shegin is always right you are wrong')"
    		   		+ "</script>"
    		   		+ "<p style='font-size: 18px; color: green; font-weight: bold; margin-top: 20px;'>"
    		   		+ "Wrong Password" +"\n" + "</p><a href='TransactionController'>  Try Again </a></li></body></html>");
	   	}
	   	else {
	   		out.println("<html><head>");
	   		out.println("<title>Transaction Table</title>");
	   		out.println("    <link rel='stylesheet' href='style_table.css'>");
	   		out.println("</head><body>");
	   		out.println("<table>"
	   				+ "    <thead>"
	   				+ "        <tr>"
	   				+ "            <th>From</th>"
	   				+ "            <th>To</th>"
	   				+ "            <th>Amount</th>"
	   				+ "            <th>Date</th>"
	   				+ "        </tr>"
	   				+ "    </thead>"
	   				+ "<tbody>");
	   		for (Map<String, Object> map:res) {
	   			out.println("<tr>"
	   					+ "            <td>" + map.get("name1") + "</td>"
	   					+ "            <td>" + map.get("name2") + "</td>"
	   					+ "            <td>" + map.get("amount") + "</td>"
	   					+ "            <td>" + map.get("date") + "</td>"
	   					+ "        </tr>");
	   		}
	   		
	   		/*
	   		for (Map<String, Object> map:res) {
	   			for (Map.Entry<String, Object> entry:map.entrySet()) {
	   				out.println("<p>" + String.valueOf(entry.get("from"))+":"+String.valueOf(entry.getValue())+"</p>");
	   			}
	   		}*/
	   		out.println("<h> Transaction History!!\n");
	   		out.println("<a href='ProfileCont'> Go Back</a></li>");
	   		
	   		out.println("</body></html>");
	   	}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

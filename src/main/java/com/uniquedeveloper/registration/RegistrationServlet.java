package com.uniquedeveloper.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	           String uname = request.getParameter("name");
	           String uemail = request.getParameter("email");
	           String upswd = request.getParameter("pass");
	           String umobile = request.getParameter("contact");  
	           RequestDispatcher dispatcher = null;
	           Connection con = null;
	           
	           try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company1?useSSL=false","root","root");
				PreparedStatement pst = con.prepareStatement("insert into user(uname,upswd,uemail,umobile) values(?,?,?,?)");
				pst.setString(1, uname);
				pst.setString(2, upswd);
				pst.setString(3, uemail);
				pst.setString(4, umobile);
				
				int rowCount = pst.executeUpdate();
				dispatcher = request.getRequestDispatcher("registration.jsp");
				if(rowCount > 0) {
					request.setAttribute("status", "Success");
					
				}else {
					request.setAttribute("status", "failed");
				}
				dispatcher.forward(request, response);
				} 
	           catch (Exception e) {
				e.printStackTrace();
				
				}
	           finally {
					try {
						con.close();
					} catch (Exception e2) {
					}
				}
	         

}
}

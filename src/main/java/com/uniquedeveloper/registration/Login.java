package com.uniquedeveloper.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail = request.getParameter("username");
		String upswd = request.getParameter("password");
		 RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company1?useSSL=false","root","root");
			PreparedStatement pst = con.prepareStatement("Select * from user where uemail = ? and upswd = ?");
			pst.setString(1, uemail);
			pst.setString(2, upswd);
			
			ResultSet rs = pst.executeQuery();
			  if(rs.next()) {
				  session.setAttribute("name", rs.getString("uname"));
				  dispatcher =  request.getRequestDispatcher(("index.jsp"));
			  }else {
				     request.setAttribute("status", "failed");
				     dispatcher =  request.getRequestDispatcher("login.jsp");
			  }
				  
				 
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

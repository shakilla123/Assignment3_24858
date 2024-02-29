package controller;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.query.Query;
import STUDENT_HIBERNATE.Hibernateutil;
import STUDENT_MODEL.User;

@WebServlet("/LoginController1")
public class LoginConroller1  extends HttpServlet  {
	
	    private static final long serialVersionUID = 1L;

	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        response.setContentType("text/html");

	        String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        try {
	            // Getting user from database based on email
	            Session session = Hibernateutil.getSessionFactory().openSession();
	            Query<User> query = session.createQuery("FROM User WHERE email=:email AND password = :password", User.class);
	            query.setParameter("email", email);
	            query.setParameter("password", password);
	            
	            
	            User user = query.uniqueResult();
	   
	           if (user != null && user.getPassword().equals(password)) {
	                // User is authenticated, create session and redirect to Student_Application_Form
	                HttpSession session1 = request.getSession();
	                session1.setAttribute("user", user);
	                session.close();// Close the session before forwarding
	                /*RequestDispatcher dispatcher=request.getRequestDispatcher("");
	               dispatcher.forward(request, response); */
	                response.sendRedirect("Student_Application_Form.html");
	            } /*else {
	                // Invalid credentials, redirect to login page
	                response.sendRedirect("Login_Form.html");
	          }  */

	        } catch (Exception e) {
	            // Handle any exceptions
	            e.printStackTrace();
	            response.getWriter().println("An error occurred during login: " + e.getMessage());
	        }
	    }
	}


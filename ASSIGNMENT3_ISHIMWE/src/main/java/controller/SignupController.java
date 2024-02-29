package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import STUDENT_HIBERNATE.Hibernateutil;
import STUDENT_MODEL.User;

@WebServlet("/SignupController")
public class SignupController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Initialize variables
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            email = request.getParameter("email");
            password = request.getParameter("password");

            // Creating user object
            User user = new User();
            user.setemail(email);
            user.setPassword(password);

            // Using Hibernate session to save user
            Session session = Hibernateutil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();

            // Redirecting to login page
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login_Form.html");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            // Handle any exceptions
            out.println("An error occurred during registration: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

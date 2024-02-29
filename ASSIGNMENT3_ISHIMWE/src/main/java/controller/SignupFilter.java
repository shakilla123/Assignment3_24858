package controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
@WebFilter("/SignupController") 
public class SignupFilter implements Filter{

	/**
	 * Servlet Filter implementation class SignupFilter
	 */
	    public SignupFilter() {
	        // Default constructor
	    }

	    public void destroy() {
	        // Cleanup resources if needed
	    }

	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	        // Get parameter values from the request
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        // Perform validation checks
	        if (isEmpty(email) || isEmpty(password)) {
	            // Handle empty fields
	            // For simplicity, let's print a message to the console
	        	response.getWriter().println("Email or password is empty.Please try again.");
	            return; // Stop processing further
	        }

	        if (!isValidEmail(email)) {
	            // Handle invalid email
	            // For simplicity, let's print a message to the console
	        	response.getWriter().println("Invalid Email Format.Please try again.");
	            return; // Stop processing further
	        }

	        // Continue with the filter chain
	        chain.doFilter(request, response);
	    }

	    public void init(FilterConfig fConfig) throws ServletException {
	        // Initialization code if needed
	    }

	    // Method to check if a string is empty or null
	    private boolean isEmpty(String str) {
	        return str == null || str.trim().isEmpty();
	    }

	    // Method to validate email format using a regular expression
	    private boolean isValidEmail(String email) {
	        // Regular expression for email validation
	        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	        
	        // Compile the regular expression pattern
	        Pattern pattern = Pattern.compile(regex);
	        
	        // Match the email against the pattern
	        Matcher matcher = pattern.matcher(email);
	        
	        // Return true if the email matches the pattern, false otherwise
	        return matcher.matches();
	    }

	}


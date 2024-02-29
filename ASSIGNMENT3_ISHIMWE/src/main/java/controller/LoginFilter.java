package controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/LoginController")
public class LoginFilter  implements Filter {
	
    public LoginFilter() {
	
}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	    HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Check if email and password are not empty
        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            // Forward the request to the next filter/servlet in the chain
            chain.doFilter(request, response);
        } else {
            // a message attribute to display on the login page
            req.setAttribute("errorMessage", "email or password cannot be empty.");

            // Forward the request to the login page
            req.getRequestDispatcher("Login_Form.html").forward(req, res);
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}

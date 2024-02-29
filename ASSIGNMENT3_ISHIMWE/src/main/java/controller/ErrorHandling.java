package controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

	public class ErrorHandling extends HttpServlet {

	    private static final long serialVersionUID = 1L;

	    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

	        PrintWriter out = res.getWriter();

	        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");

	        Integer status = (Integer) req.getAttribute("javax.servlet.error.status_code");

	        String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");

	        if (servletName == null) {
	            servletName = "Unknown";
	        }

	        res.setContentType("text/html");

	        out.println("<html><head><title>Error Handling</title></head><body>");

	        if (status != null) {
	            switch (status) {
	                case 400:
	                    out.println("<h2>400 Bad Request</h2>");
	                    out.println("<p>The request could not be understood by the server due to malformed syntax.</p>");
	                    break;
	                case 401:
	                    out.println("<h2>401 Unauthorized</h2>");
	                    out.println("<p>The request requires user authentication.</p>");
	                    break;
	                case 403:
	                    out.println("<h2>403 Forbidden</h2>");
	                    out.println("<p>The server understood the request but refuses to authorize it.</p>");
	                    break;
	                case 404:
	                    out.println("<h2>404 Not Found</h2>");
	                    out.println("<p>The resource you are looking for could not be found.</p>");
	                    break;
	                // Add more cases for other status codes as needed
	                default:
	                    out.println("<h2>Error " + status + "</h2>");
	                    out.println("<p>An error occurred while processing the request.</p>");
	                    break;
	            }
	        } else {
	            out.println("<h2>Error</h2>");
	            out.println("<p>An unknown error occurred.</p>");
	        }

	        out.println("</body></html>");
	    }

	    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
	        doGet(req, res);
	    }
	}


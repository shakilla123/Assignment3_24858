package controller;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import STUDENT_HIBERNATE.Hibernateutil;
import STUDENT_MODEL.Student_Model;

@WebServlet("/upload")
@MultipartConfig
public class StudentAppController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SessionFactory sessionFactory;

    public StudentAppController() {
        super();
        sessionFactory = Hibernateutil.getSessionFactory();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");

        // Get the uploaded files
        Part passportPicture = (Part) request.getPart("passport");
        Part certificatePDF = (Part) request.getPart("certificate");

        // Save form data to database using Hibernate
        saveFormDataToDatabase(firstName, lastName, gender, email, certificatePDF, passportPicture);

        // Send submission confirmation email
        sendConfirmationEmail(email);

        response.sendRedirect("index.html?message=Files uploaded successfully and confirmation email sent");
    }

    private void saveFormDataToDatabase(String firstName, String lastName, String gender, String email, Part passportPicture, Part certificatePDF) {
        org.hibernate.Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            // Begin a transaction
            tx = session.beginTransaction();

            // Create and save student
            Student_Model student = new Student_Model();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setGender(gender);
            student.setEmail(email);

            // Convert and save passport picture
            if (passportPicture != null && passportPicture.getSize() > 0) {
                student.setPassportPicture(IOUtils.toByteArray(passportPicture.getInputStream()));
            }

            // Convert and save certificate PDF
            if (certificatePDF != null && certificatePDF.getSize() > 0) {
                student.setCertificatePDF(IOUtils.toByteArray(certificatePDF.getInputStream()));
            }

            // Save the student entity
            session.save(student);

            // Commit the transaction
            tx.commit();
        } catch (Exception ex) {
            // Rollback the transaction if there's an exception
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close the session
            session.close();
        }
    }

    private void sendConfirmationEmail(String recipientEmail) {
        final String username = "ishimweshakila8@gmail.com"; // Your email address
        final String password = "shakilla@8Ishimwe"; // Your email password

        // Email host properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Assuming you're using Gmail
        props.put("mail.smtp.port", "587");

        // Add SSL properties
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");

        // Create session
        javax.mail.Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set sender email address
            message.setFrom(new InternetAddress(username));

            // Set recipient email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Set email subject
            message.setSubject("Admission Application Confirmation");

            // Set email content
            message.setText("Dear Student,\n\nYour admission application has been received successfully.\n\nBest regards,\nThe Admission Team");

            // Send the email
            Transport.send(message);

            System.out.println("Confirmation email sent to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

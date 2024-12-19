package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.EmailSender;
import utils.EmailTemplates;
import utils.MessageTemplate;
import models.Contact;
import models.ContactType;
import models.User;

@WebServlet("/resend_verification_mail.do")
public class ResendVerificationMailServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        String msg = MessageTemplate.getErrorMessage();
        String type = "error";
        String nextPage = "message.jsp?type="+ type +"&message="+msg;

        String email = request.getParameter("email");

        User user = new User();
        Contact contact = new Contact(user,email,new ContactType(ContactType.EMAIL1));

        if(contact.checkContact()){
            user.getDetails();
            String mailContent = EmailTemplates.generateWelcomeMail(user.getName(), email, user.getActivationCode(), user.getRole());
            String subject = "Email Verification Mail";
            EmailSender.sendEmail(email, subject, mailContent);
            msg = MessageTemplate.resendEmailSuccessful(email);
            type = "success";
            nextPage = "message.jsp?type="+ type +"&message="+msg;
        }

        request.getRequestDispatcher(nextPage).forward(request, response);
    }
}

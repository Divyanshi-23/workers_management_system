package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Contact;
import models.ContactType;
import models.User;
import utils.AppUtils;
import utils.EmailSender;
import utils.EmailTemplates;

@WebServlet("/forgot_password.do")
public class ForgotPasswordServlet extends HttpServlet {
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        String email = request.getParameter("email");
        String activationCode = AppUtils.generatePasswordResetCode();

        User user = new User(activationCode);
        Contact contact = new Contact(user,email,new ContactType(ContactType.EMAIL1));
        String nextPage = "error.jsp";
        if(contact.checkContact()){
            if(user.setActivationCode()){
                user.getDetails();
                String subject = "Email Verification Mail";
                String emailContent = EmailTemplates.generatePasswordResetMail(user.getName(), email,user.getUserName(),user.getActivationCode());
                EmailSender.sendEmail(email, subject, emailContent);
                nextPage = "success.jsp";
                //message template
            }
        }
        request.getRequestDispatcher(nextPage).forward(request, response);
    }
    
}

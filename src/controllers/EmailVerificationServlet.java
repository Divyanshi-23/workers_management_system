package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import models.User;
import models.Contact;
import models.ContactType;

@WebServlet("/email_verify.do")
public class EmailVerificationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        String email = request.getParameter("email");
        String activationCode = request.getParameter("activation_code");
        Boolean role = Boolean.parseBoolean(request.getParameter("role"));
        
        User user = new User(activationCode);
        user.setRole(role);
        Contact contact = new Contact(user,email,new ContactType(ContactType.EMAIL1));

        String nextPage = "error.jsp";

        if(contact.checkContact()){
            if(user.verifyEmail()){
                nextPage = "success.jsp";
            }
        }
        response.sendRedirect(nextPage);
    }
}

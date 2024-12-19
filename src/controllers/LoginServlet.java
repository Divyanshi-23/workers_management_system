package controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;

import models.ContactType;
import models.Contact;
import models.Status;
import models.User;
import utils.MessageTemplate;

@WebServlet("/login.do")

public class LoginServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        request.getRequestDispatcher("login.jsp").forward(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        HttpSession session = request.getSession();
        String msg = MessageTemplate.getErrorMessage();
        String type = "error";
        String nextPage = "message.jsp?type="+ type +"&message="+msg;
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User();
        user.setPassword(password);
        Contact contact = new Contact(user,email,new ContactType(ContactType.EMAIL1));
        
        if(contact.checkContact()){
            int result = user.login();
            ArrayList<Contact> contacts = Contact.getAllContacts(user);
            if(result == 3){
                session.setAttribute("user", user);
                session.setAttribute("contacts", contacts);
                session.setAttribute("email", email);
                if(user.getStatus().getStatusId()==Status.ACTIVE)
                    nextPage = "dashboard.do";
                else
                    nextPage = "add_details.jsp";    
            }else if(result == 2){
                msg = MessageTemplate.getIncompleteEmailVerificationMessage(email);
                type = "warning";
                nextPage = "message.jsp?type="+ type +"&message="+msg;
            }else if(result == 1){
                msg = MessageTemplate.incorrectPasswordMessage();
                type = "error";
                nextPage = "message.jsp?type="+ type +"&message="+msg;
            }
        }
        request.getRequestDispatcher(nextPage).forward(request,response);
    }
    
}

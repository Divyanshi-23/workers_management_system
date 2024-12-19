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


@WebServlet("/reset_password.do")
public class ResetPasswordServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        request.getRequestDispatcher("reset_password.jsp?email="+request.getParameter("email")).forward(request, response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String activationCode = request.getParameter("activation_code");

        User user = new User(activationCode);
        user.setPassword(password);
        Contact contact = new Contact(user,email,new ContactType(ContactType.EMAIL1));
        String nextPage = "error.jsp";

        if(contact.checkContact()){
            if(user.resetPassword())
                nextPage = "success.jsp";
        }
        request.getRequestDispatcher(nextPage).forward(request, response);
    }
}

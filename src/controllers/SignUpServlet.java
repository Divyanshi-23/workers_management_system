package controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Date;

import models.User;
import utils.AppUtils;
import utils.EmailTemplates;
import utils.EmailSender;
import models.City;
import models.Contact;
import models.ContactType;

import java.io.File;

@WebServlet("/signup.do")
public class SignUpServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        request.getRequestDispatcher("register.jsp").forward(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        ServletContext context = getServletContext();

        String name = request.getParameter("name");
        String userName = request.getParameter("user_name");
        String email1 = request.getParameter("email1");
        String email2 = request.getParameter("email2");
        String mobile1 = request.getParameter("mobile1");
        String mobile2 = request.getParameter("mobile2");
        String gender = request.getParameter("gender");
        Date dob = Date.valueOf(request.getParameter("dob"));
        String profilePic = request.getParameter("profile_pic");
        String password = request.getParameter("password");
        Integer cityId = Integer.parseInt(request.getParameter("city_id"));
        String uId = request.getParameter("u_id");
        String address = request.getParameter("address");
        Boolean role = Boolean.parseBoolean(request.getParameter("role"));

        String activationCode = AppUtils.generateEmailActivationCode();
        String nextPage = "error.jsp";

        User user = new User(userName, name, gender, dob, profilePic, password, new City(cityId), uId, role, activationCode);
        if(user.saveRecord()){
            if(
                (new Contact(user, mobile1, new ContactType(ContactType.PHONE1))).saveContact() &&
                (new Contact(user, mobile2, new ContactType(ContactType.PHONE2))).saveContact() &&
                (new Contact(user, email1, new ContactType(ContactType.EMAIL1))).saveContact() &&
                (new Contact(user, email2, new ContactType(ContactType.EMAIL2))).saveContact()&&
                (new Contact(user, address, new ContactType(ContactType.ADDRESS))).saveContact()
            ){
                String subject = "Email Verification Mail";
                String emailContent = EmailTemplates.generateWelcomeMail(name, email1, activationCode,role);
                EmailSender.sendEmail(email1, subject, emailContent);
                String folder = "customers";
                if(role == true){
                    folder = "workers";
                }
                String userPath = context.getRealPath("/WEB-INF/uploads/"+folder);
                File file = new File(userPath, email1);
                file.mkdir();
                nextPage = "login.jsp";
            }
        }
        request.getRequestDispatcher(nextPage).forward(request,response);
    }
}

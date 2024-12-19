package controllers;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;

import models.User;

@WebServlet("/edit_profile.do")
public class EditProfileServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Boolean role = user.getRole();

        if(role){
            request.getRequestDispatcher("edit_worker_profile.jsp").forward(request,response);
        }else{
            request.getRequestDispatcher("edit_customer_profile.jsp").forward(request,response);
        }
    }
}

package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import utils.MessageTemplate;

@WebServlet("/dashboard.do")
public class DashBoardServlet extends HttpServlet{

    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            ArrayList<User> workersInArea = User.findWorkerInArea(user.getCity());
            session.setAttribute("allWorkerInArea", workersInArea);
            session.setAttribute("record_size", workersInArea.size());
            
            request.getRequestDispatcher("dashboard.jsp").forward(request,response);
        }
        else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response) ;
        }
        
    }
}

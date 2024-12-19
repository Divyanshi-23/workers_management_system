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
import models.WorkerSkill;
import utils.MessageTemplate;

@WebServlet("/profile.do")
public class ProfileServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String nextPage = null;
        if(user!=null){
            if(user.getRole()){
                nextPage = "worker_profile.jsp";
                ArrayList<WorkerSkill> workerSkills = WorkerSkill.getAllSkills(user);
                session.setAttribute("worker_skills", workerSkills);

            }else{
                nextPage = "customer_profile.jsp";
            }
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            nextPage = "message.jsp?type=warning&message="+msg;

        }
        request.getRequestDispatcher(nextPage).forward(request, response);
    }
}

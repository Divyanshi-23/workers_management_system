package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import models.User;
import models.WorkerSkill;

@WebServlet("/show_complete_worker_profile.do")
public class ShowCompleteProfile extends HttpServlet {
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            int uId = Integer.parseInt(request.getParameter("user_id"));
            User u = new User(uId);
            u.getDetails();
            ArrayList<WorkerSkill> workerSkills = WorkerSkill.getAllSkills(u);

            session.setAttribute("worker", u);
            session.setAttribute("worker_skill", workerSkills);

            request.getRequestDispatcher("enquire_dashboard_worker.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("login.do").forward(request, response);
        }
        
    }
}

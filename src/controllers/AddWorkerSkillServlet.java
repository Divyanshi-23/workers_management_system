package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import models.Skill;
import models.WorkerSkill;

@WebServlet("/add_skills.do")
public class AddWorkerSkillServlet extends HttpServlet{
        public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        Integer skillId = Integer.parseInt(request.getParameter("skill_id"));
        Integer experience = Integer.parseInt(request.getParameter("experience"));

        User user = (User)session.getAttribute("user");
        
        WorkerSkill workerSkill = new WorkerSkill(user,new Skill(skillId),experience);

        if(workerSkill.saveSkill()){
            user.activateAccount();
            request.getRequestDispatcher("dashboard.do").forward(request,response);
        }else{
            //error handling
        }
        
        //response.sendRedirect("index.jsp");
    }
}

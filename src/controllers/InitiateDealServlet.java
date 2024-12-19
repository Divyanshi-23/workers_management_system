package controllers;

import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import models.Deal;
import models.Skill;
import models.User;
import models.WorkerSkill;
import models.Contact;
import utils.MessageTemplate;

@WebServlet("/initiate_deal.do")
public class InitiateDealServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            String email = request.getParameter("email");
            Contact contact = new Contact(email);
            String description = request.getParameter("description");
            int skillId = Integer.parseInt(request.getParameter("skill_id"));
            
            WorkerSkill workerSkill = new WorkerSkill(user,new Skill(skillId));
            workerSkill.getSkillDetails();

            Timestamp registrationDate = new Timestamp(new java.util.Date().getTime());

            Integer amount = Integer.parseInt(request.getParameter("amount"));
            Date sDate = Date.valueOf(request.getParameter("s_date"));
            Date eDate = Date.valueOf(request.getParameter("e_date"));
            User u1 = new User(contact.getUserIdFromContact());
            
            Deal deal = new Deal(workerSkill,u1,registrationDate,description,sDate,eDate,amount,null,null,null,null,null); 
            boolean rs = deal.initiateDeal();
            if(!rs){
                deal.startDeal();
            }
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

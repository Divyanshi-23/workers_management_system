package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import models.WorkerSkill;
import utils.MessageTemplate;
import models.Deal;

@WebServlet("/enquire.do")
public class EnquireDealServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        
        if(user!=null ){
            Integer wSkillId = Integer.parseInt(request.getParameter("worker_skill_id"));
            WorkerSkill ws = new WorkerSkill(wSkillId);
            ws.getSkillDetailsWithWorkerSkillId();
            
            Deal deal = new Deal(ws,user);

            if(deal.getEnquiredDeal() == false){
                System.out.println("+++++++++++++++++++++++");
                Timestamp registrationDate = new Timestamp(new java.util.Date().getTime());
                deal.setRegistrationDate(registrationDate);
                boolean rs = deal.createDeal();
                if(rs){
                    deal.getDealDetailWithDealId();
                }else{
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
            session.setAttribute("deal", deal);
            request.getRequestDispatcher("message.do").forward(request, response);
            
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

package controllers;

import java.util.ArrayList;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Deal;
import models.DealStatus;
import models.User;
import models.WorkerSkill;
import utils.MessageTemplate;

@WebServlet("/show_running_deals.do")
public class ShowRunningDealsServlet extends HttpServlet{
    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            if(user.getRole()){
                ArrayList<WorkerSkill> workerSkills = WorkerSkill.getAllSkills(user);
                ArrayList<Deal> allDeals = new ArrayList<>();

                for(WorkerSkill wSkill : workerSkills){
                    ArrayList<Deal> deals = Deal.getDealsByWorkerSkill(wSkill);
                    for(Deal d : deals){
                        if(d.getDealStatus().getDealStatusId()!=DealStatus.CANCELLED &&d.getDealStatus().getDealStatusId()!=DealStatus.REJECTED && d.getDealStatus().getDealStatusId()!=DealStatus.ENQUIRED ){
                            allDeals.add(d);
                        }
                    }
                }
                session.setAttribute("running_deals", allDeals);
                request.getRequestDispatcher("show_running_deal.jsp").forward(request, response);
                
            }else{
                ArrayList<Deal> deals = Deal.getAllDeals(user);
                ArrayList<Deal> allDeals = new ArrayList<>();

                for(Deal d : deals){
                    if(d.getDealStatus().getDealStatusId()!=DealStatus.CANCELLED &&d.getDealStatus().getDealStatusId()!=DealStatus.REJECTED && d.getDealStatus().getDealStatusId()!=DealStatus.ENQUIRED ){
                        allDeals.add(d);
                    }
                }
                
                session.setAttribute("running_deals", allDeals);
                request.getRequestDispatcher("show_running_deal.jsp").forward(request, response);
            }
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

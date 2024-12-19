package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Deal;
import models.User;
import utils.MessageTemplate;

@WebServlet("/pending_deals.do")
public class PendingDealsServlet extends HttpServlet{
    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){

            ArrayList<Deal> deals = Deal.getAllDeals(user);
            ArrayList<Deal> pendingDeals = new ArrayList<>();
            Date todayDate = new Date();
            for(Deal d : deals){
                Date dealEndDate = (Date)d.getEndDate();
                if(todayDate.getTime()>dealEndDate.getTime()){
                    pendingDeals.add(d);
                }
            }
            session.setAttribute("pending_deals", pendingDeals);
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

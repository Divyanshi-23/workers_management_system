package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Deal;
import models.User;
import utils.MessageTemplate;

public class ViewProgressServlet {
    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            Deal deal = null ;
            Integer dealId = Integer.parseInt(request.getParameter("deal_id"));
            if(user.getRole()){
                ArrayList<Deal> deals = Deal.getAllDeals(user);
                for(Deal d : deals){
                    if(d.getDealId()==dealId){
                        deal = d;
                    }
                    session.setAttribute("deal_progress", deal);
                    request.getRequestDispatcher("show_progress.jap").forward(request, response);
                }

            }else{
                // if working is searching
            }

        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Deal;
import models.User;
import utils.MessageTemplate;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/show_deal_request.do")
public class ShowDealRequestServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            ArrayList<Deal> deals = Deal.getInitiationRequestedDeals(user);
            session.setAttribute("request_deals", deals);
            request.getRequestDispatcher("show_requested_deals.jsp").forward(request,response);
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    
    }
}

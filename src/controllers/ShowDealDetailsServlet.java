package controllers;

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

@WebServlet("/show_deal_details.do")
public class ShowDealDetailsServlet extends HttpServlet{
    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            Integer dealId = Integer.parseInt(request.getParameter("deal_id"));
            Deal  deal = new Deal(dealId);
            deal.getDealDetail();

            session.setAttribute("this_deal", deal);
            request.getRequestDispatcher("show_deal_details.jsp").forward(request, response);

        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

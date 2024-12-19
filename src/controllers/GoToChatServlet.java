package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import utils.MessageTemplate;
import models.Deal;

@WebServlet("/go_to_chat.do")
public class GoToChatServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        
        if(user!=null ){
            Deal deal = new Deal(Integer.parseInt(request.getParameter("deal_id")));
            deal.getDealDetailWithDealId();

            session.setAttribute("deal", deal);
            request.getRequestDispatcher("message.do").forward(request, response);
            
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

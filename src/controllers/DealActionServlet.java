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

@WebServlet("/deal_action.do")
public class DealActionServlet extends HttpServlet{
    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            String action = request.getParameter("action");
            Integer dealId = Integer.parseInt(request.getParameter("deal_id"));
            Deal deal = new Deal(dealId);
            if(action.equals("accept")){
                boolean result = deal.acceptDeal();

                if(result){
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                }else{
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }else{
                boolean result = deal.rejectDeal();
                if(result){
                    request.getRequestDispatcher("success.jsp").forward(request, response);
                }else{
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

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
import models.WorkStatus;
import utils.MessageTemplate;

@WebServlet("/update_work_status.do")
public class UpdateWorkStatus extends HttpServlet{
    @SuppressWarnings("unused")
    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            Deal deal = (Deal)session.getAttribute("this_deal");
            Integer statusId = null;
            try{
                statusId = Integer.parseInt(request.getParameter("payment_completed"));
                deal.paymentCompleted();
            }catch(NumberFormatException e){
                try{
                    statusId = Integer.parseInt(request.getParameter("working"));

                }catch(NumberFormatException e1){
                    try{
                        statusId = Integer.parseInt(request.getParameter("reached_client"));
                    }catch(NumberFormatException e2){
                        statusId = Integer.parseInt(request.getParameter("initiated"));
                    }
                }
            }
            deal.setWorkStatus(new WorkStatus(statusId));
            boolean rs = deal.updateWorkStatus();
            

            request.getRequestDispatcher("show_deal_details.do?deal_id="+deal.getDealId()).forward(request,response);
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

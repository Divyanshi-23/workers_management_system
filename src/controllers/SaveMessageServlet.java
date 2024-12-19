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
import models.Chat;
import models.Deal;

@WebServlet("/save_message.do")
public class SaveMessageServlet extends HttpServlet{
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        String msg = request.getParameter(("message"));
        Deal deal = (Deal)session.getAttribute("deal");
        boolean fromTo = false;

        if(deal.getUser().getUserId() == user.getUserId()){
            fromTo = true;
        }
        
        if(user!=null){
            Chat chat = new Chat(deal.getWorkerSkill(),deal.getUser(),fromTo,new Timestamp(System.currentTimeMillis()),msg,deal);
            boolean rs = chat.saveChat();
            if(rs){
                request.getRequestDispatcher("chat.jsp").forward(request, response);
            }
            else{
                System.out.println("+++++++++error+++++++++");
            }
        }
    }
}

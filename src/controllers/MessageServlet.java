package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import utils.MessageTemplate;
import models.Chat;
import models.Deal;

@WebServlet("/message.do")
public class MessageServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if(user!=null){
            Deal deal =(Deal)session.getAttribute("deal");
            ArrayList<Chat> chats = Chat.getChats(deal);

            session.setAttribute("chats", chats);
            
            
            request.getRequestDispatcher("chat.jsp").forward(request, response);
            
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}

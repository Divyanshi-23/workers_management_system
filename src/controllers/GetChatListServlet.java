// package controllers;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.LinkedHashSet;

// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;

// import models.User;
// import models.Chat;

// // -------------------------incomplete----------------------------

// @WebServlet("/get_chat_list.do")
// public class GetChatListServlet extends HttpServlet{
//     @SuppressWarnings("unchecked")
//     public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
//         HttpSession session = request.getSession();
//         User user = (User)session.getAttribute("user");

//         if(user!=null){
            
//             ArrayList<Chat> allChats = new ArrayList<>();

//             for(Chat ch : sentChats){
//                 allChats.add(ch);
//             }
//             for(Chat ch : receivedChats){
//                 allChats.add(ch);
//             }
//             // Collections.sort(allChats);

//             ArrayList<User> arr = new ArrayList<>();
            
//             for(Chat c : allChats){
//                 c.getSender().getDetails();
//                 arr.add(c.getSender());
//                 c.getReceiver().getDetails();
//                 arr.add(c.getReceiver());
//             }
//             LinkedHashSet<Integer> chatList = new LinkedHashSet<>();

//             // for(User)
            

//             request.getRequestDispatcher("chat.jsp").forward(request, response);
//         }else{

//         }
        
//     }
// }

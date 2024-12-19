package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;

@WebServlet("/logo.do")
public class LogoServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
        User user;
        String path;
        if(request.getParameter("profile_path")!=null){
            path = request.getParameter("profile_path");
        }else{
            user = (User)session.getAttribute("user");
            path = user.getProfilePic();
        }
        if(path != null) {
            InputStream is = context.getResourceAsStream(path);
            OutputStream os = response.getOutputStream();
            
            byte[] arr = new byte[256];
            
            while(is.read(arr) != -1) {
                os.write(arr);
            }
            os.flush();
            os.close();
        }        
    }
}

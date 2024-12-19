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

@WebServlet("/skill_image.do")
public class SkillImageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        
        String path = request.getParameter("path");
        
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

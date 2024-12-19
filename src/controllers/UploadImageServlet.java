package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import models.Contact;
import models.User;
import utils.MessageTemplate;

@WebServlet("/upload_images.do")
public class UploadImageServlet extends HttpServlet {
    public void doPost(HttpServletRequest request , HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        ServletContext context = getServletContext();

        Contact contact = new Contact(user);
        contact.getUserEmail();
        String email = contact.getContact();

        Integer skillId = null;

        if(user!=null){
            if(ServletFileUpload.isMultipartContent(request)){
                try {
                    List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                    for(FileItem item : items){
                        if(item.isFormField()){
                            skillId = Integer.parseInt(item.getString());
                        }
                        else{
                            String fileName = "work_image_." + item.getName();   
                            String uploadPath = context.getRealPath("/WEB-INF/uploads/workers/work_images" + email +"/work_images/" + skillId);
            
                            File file = new File(uploadPath, fileName);
            
                            try{ item.write(file); } catch(Exception e) { e.printStackTrace(); }    
                        }
                            
                    } 
                }catch(FileUploadException e) {
                    e.printStackTrace();
                }                 
            }       
        }else{
            String msg = MessageTemplate.sessionExpiredMessage();
            request.getRequestDispatcher("message.jsp?type=warning&message="+msg).forward(request,response);
        }
    }
}


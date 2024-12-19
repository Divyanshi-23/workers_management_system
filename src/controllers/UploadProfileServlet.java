package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
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

@WebServlet("/upload_profile.do")
public class UploadProfileServlet extends HttpServlet {
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        HttpSession session = request.getSession();
        User user =(User)session.getAttribute("user");
        ServletContext context = getServletContext();
        Contact contact = new Contact(user);
        contact.getUserEmail();

        if(ServletFileUpload.isMultipartContent(request)){
            try{
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                FileItem item = items.get(0);
                String fileName = "profile_pic." + item.getName().split("[.]")[1];
                String folder = "customers";
                if(user.getRole()){
                    folder = "workers";
                }
                String uploadPath = "/WEB-INF/uploads/" + folder + "/" + contact.getContact() + "/profile_pic/";
                File f = new File(uploadPath);
                String[] list = f.list();
                if(list.length == 1){
                    System.out.print(new File(uploadPath,list[0]).delete());
                }
                System.out.println(uploadPath);
                System.out.println(fileName);
                
                File file = new File(uploadPath, fileName);
                user.setProfilePic(uploadPath + fileName);
                try{ 
                    item.write(file); 
                    user.updateProfilePic();
                } catch(Exception e) { e.printStackTrace(); }
                
            }catch(FileUploadException e){
                e.printStackTrace();
            }
        }   
        
    }
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        doPost(request, response);
    }
}

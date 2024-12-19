package tlds;

import java.io.File;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import models.Contact;
import models.User;

public class SkillImageTagHandler extends SimpleTagSupport{
    public Integer userId;
    public Integer skillId;

    public void doTag()throws IOException,JspException{
        User user = new User(userId);
        user.getDetails();
        Contact contact = new Contact(user);
        contact.getUserEmail();
        String path = ((PageContext)getJspContext()).getServletContext().getRealPath("/WEB-INF/uploads/workers/" + contact.getContact() + "/work_images/" + skillId);
        
        File folder = new File(path);
        File[] fileList = folder.listFiles();
        if(folder.exists()){
            for ( int i = 0; i < fileList.length; i++) {
                String str = fileList[i].getPath().split("wms")[1];
                str = str.replace("\\","/");
                
                getJspContext().setAttribute("insertStr",str);
                getJspBody().invoke(null);
            }
        }else{
            getJspBody().invoke(null);
        }
    }
    public void setUserId(Integer userId){
        this.userId = userId;
    }
    public void setSkillId(Integer skillId){
        this.skillId = skillId;
    }
}

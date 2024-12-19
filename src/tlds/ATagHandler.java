package tlds;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import models.Skill;
import models.User;

public class ATagHandler extends SimpleTagSupport {
    User user;
    Skill skill;

    public void setUser(User user) {
        this.user = user;
    }
    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public void doTag()throws IOException,JspException{
        JspFragment frag = getJspBody();
        JspContext context = getJspContext();

        context.setAttribute("thisUser", user);
        context.setAttribute("thisSkill", skill);

        frag.invoke(null);
    }
}

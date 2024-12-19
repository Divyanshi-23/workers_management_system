package listeners;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.ArrayList;

import models.City;
import models.Skill;
import utils.EmailSender;

public class ContextListenerImpl implements ServletContextListener{
    public void contextInitialized(ServletContextEvent event){
        ServletContext context = event.getServletContext();

        EmailSender.fromEmail = context.getInitParameter("from_email");
        EmailSender.password = context.getInitParameter("from_email_password");

        ArrayList<City> cities = City.collectAllCities();
        context.setAttribute("cities",cities);

        ArrayList<Skill> skills = Skill.collectAllSkills();
        context.setAttribute("skills",skills);      
    }

    public void contextDestroyed(ServletContextEvent event){
        System.out.println("++++++++++++Context Destroyed++++++++++++");
    }
}

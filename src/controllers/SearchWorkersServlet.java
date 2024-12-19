package controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import models.User;
import models.City;
import models.WorkerSkill;

@WebServlet("/search_worker.do")
public class SearchWorkersServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
        HttpSession session = request.getSession();
        Integer skillId = Integer.parseInt(request.getParameter("skill_id"));
        String cityId = request.getParameter("city_id");
        City city;
        try{
            User user = (User)session.getAttribute("user");
            if(cityId==null){
                city = user.getCity();
            }else{
                city = new City(Integer.parseInt(cityId));
            }
            
            ArrayList<WorkerSkill> searchedWorkerSkill = WorkerSkill.findWorkers(city,skillId);
            session.setAttribute("searchedWorkerSkill", searchedWorkerSkill);
            request.getRequestDispatcher("SearchList.jsp").forward(request, response);

        }catch(NullPointerException e){
            request.getRequestDispatcher("login.do").forward(request, response);

        }

    }
}

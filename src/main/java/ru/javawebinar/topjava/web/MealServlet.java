package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

/**
 * Created by bender on 11.04.2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("method post");
        int calories=Integer.parseInt(request.getParameter("cals"));
        String description=request.getParameter("descript");
        LocalDateTime dateTime=LocalDateTime.parse(request.getParameter("date"));
        if("".equals(request.getParameter("id"))){
            Meal meal=new Meal(dateTime,description,calories);
            meals.add(meal);
        }
        else
            {
                int id=Integer.parseInt(request.getParameter("id"));
                for(Meal m:meals){
                    if(m.getId()==id){
                       m.setDateTime(dateTime);
                       m.setDescription(description);
                       m.setCalories(calories);
                    break;}
                }
            }
        response.sendRedirect("/topjava/meals");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<MealWithExceed> mealWithExceed=getFilteredWithExceeded(meals,LocalTime.of(0,0),LocalTime.of(23,59),2000);
    request.setAttribute("mealList",mealWithExceed);
    String strId=request.getParameter("id");
        if(null!=strId) {
            int id = Integer.parseInt(strId);
            for(int i=0;i<meals.size();i++)
                if(id==meals.get(i).getId()){meals.remove(i);break;}

         mealWithExceed=getFilteredWithExceeded(meals,LocalTime.of(0,0),LocalTime.of(23,59),2000);
         request.setAttribute("mealList",mealWithExceed);
        }
//        response.sendRedirect("meals.jsp");
        request.getRequestDispatcher("/meals.jsp").forward(request,response);
    }
}
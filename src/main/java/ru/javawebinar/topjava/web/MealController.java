package ru.javawebinar.topjava.web;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping(value = "/meals")
public class MealController extends MealRestController{
    public MealController(MealService service) {
        super(service);
    }

    @RequestMapping//(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals",
                MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()),AuthorizedUser.getCaloriesPerDay()));
        return "meals";}

    @RequestMapping(value="/meal",method = RequestMethod.GET)
    public String meal(){
        return "meal";
    }

    @RequestMapping(value="/create",method = RequestMethod.GET)
    public String mealCreate(Model model){
        model.addAttribute("action","Create");
        model.addAttribute("meal",new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),"",100));
        return "meal";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String save(HttpServletRequest request){
        LocalDateTime dateTime=LocalDateTime.parse(request.getParameter("dateTime"));
        String description=request.getParameter("description");
        int calories=Integer.parseInt(request.getParameter("calories"));
        Integer mealId=Integer.valueOf(request.getParameter("id"));
        if(mealId!=null){
            service.update(new Meal(mealId,dateTime,description,calories),AuthorizedUser.id());
        }else{
        service.save(new Meal(dateTime,description,calories),AuthorizedUser.id());}
        return"redirect:/meals";
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String mealUpdate(Model model,HttpServletRequest request){
        model.addAttribute("action","Update");
        int mealId=Integer.parseInt(request.getParameter("id"));
        int userId=AuthorizedUser.id();
        model.addAttribute("meal",service.get(mealId,userId));
        return "meal";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String delete(HttpServletRequest request){
        int mealId=Integer.parseInt(request.getParameter("id"));
        int userId=AuthorizedUser.id();
        service.delete(mealId,userId);
        return"redirect:/meals";
    }
    @RequestMapping(value="/filter",method=RequestMethod.POST)
    public String filter(HttpServletRequest request, Model model){
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        int userId=AuthorizedUser.id();
        model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()));
        return"/meals";
    }
}

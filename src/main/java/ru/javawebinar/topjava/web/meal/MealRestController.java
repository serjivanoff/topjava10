package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll(int userId){
    return MealsUtil.getWithExceeded(service.getAll(userId),MealsUtil.DEFAULT_CALORIES_PER_DAY);
}

    public Meal get(int userId, int id){
    return service.get(userId,id);
}
    public Meal create(int userId,Meal meal){
    checkNew(meal);
    return service.save(userId,meal);
}
    public void update(int userId,Meal meal,int id){
    checkIdConsistent( meal,id);
    service.save(userId,meal);
}
    public void delete(int userId, int id){
    service.delete(userId,id);
}
}
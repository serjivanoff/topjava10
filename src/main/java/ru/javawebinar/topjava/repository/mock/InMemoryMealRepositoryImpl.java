package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for(Meal m:MealsUtil.MEALS){
            save(1,m);
        }

    }

    @Override
    public Meal save(int userId,Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        if(meal.getUserId()==userId){
        repository.put(meal.getId(), meal);
        return meal;}
        return null;
    }

    @Override
    public boolean delete(int userId,int id) {
        Meal meal=repository.get(id);
        if(meal==null)return false;
        if(meal.getUserId()==userId){repository.remove(id);
            return true;}else return false;
    }

    @Override
    public Meal get(int userId,int id) {
        Meal meal=repository.get(id);
        if(meal.getId()==userId){
        return meal;}
        else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
       List<Meal> meals=new ArrayList<>();
        for(Meal m:repository.values()){
        if(m.getUserId()==userId)meals.add(m);
        }
        if(meals.isEmpty())return null;
        Collections.sort(meals, new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });
        return meals;
    }
}


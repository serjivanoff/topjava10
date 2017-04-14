package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {
@Autowired
    private MealRepository repository;
    @Override
    public List<Meal> getAll(int userId) {
        System.out.println("inside service");
        List<Meal>result=repository.getAll(userId);
        if(result==null) {
            throw new NotFoundException("No meal is belong to you");
        }
        return result;
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Meal result=repository.save(userId,meal);
        if(result==null) {
            throw new NotFoundException("You can't update this meal, it's not your's");
        }
        return result;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal result=repository.get(userId,id);
        if(result==null) {
            throw new NotFoundException("it's not your's");
        }
        return result;
    }

    @Override
    public boolean delete(int userId, int id) {
        return repository.delete(userId,id);
    }
}
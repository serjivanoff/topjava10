package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    List<Meal>getAll(int userId);
    Meal save(int userId, Meal meal);
    Meal get(int userId, int id);
    boolean delete(int userId, int id);
}
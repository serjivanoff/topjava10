package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.Meal;

import java.io.Serializable;

public class MealTo implements Serializable{
    private Integer id;
    private String description;
    private int calories;
    private String dateTime;

    public MealTo(){
    }
    public Meal createMeal(){
        return null;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
// getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();

        for(UserMealWithExceed b:getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(20,0), 2009)){
            System.out.println(b);
        }
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer>caloriesSumByDay=
                mealList.stream()
                .collect(Collectors.groupingBy(meal->meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(meal->meal.getCalories())));
        List<UserMealWithExceed>list
                = mealList.stream()
                .filter(s->(TimeUtil.isBetween(s.getDateTime().toLocalTime(),startTime,endTime)&&
                                (caloriesSumByDay.get(s.getDateTime().toLocalDate())>caloriesPerDay)))
//                s.getCalories()>caloriesPerDay))
                .map(e->new UserMealWithExceed(e.getDateTime(),e.getDescription(),e.getCalories(),true))
                .collect(Collectors.toList());
        return list;
    }
}

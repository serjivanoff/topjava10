package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ+2;
    public static final Meal MEAL1=new Meal(MEAL_ID,LocalDateTime.of(2017,4,19,9,21),"завтрак",1500);
    public static final Meal MEAL2=new Meal(MEAL_ID+1,LocalDateTime.of(2017,4,19,9,22),"обед",1500);
    public static final Meal MEAL3=new Meal(MEAL_ID+2,LocalDateTime.of(2017,4,19,0,59),"ужин",100);
    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
//                          && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );

}

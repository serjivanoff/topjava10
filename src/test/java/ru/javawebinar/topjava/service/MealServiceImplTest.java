package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static ru.javawebinar.topjava.MealTestData.*;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceImplTest {
    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal test=service.get(MEAL_ID+1,ADMIN_ID);
        MATCHER.assertEquals(MEAL2,test);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_ID+1,ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3), service.getAll(ADMIN_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL2),
                service.getBetweenDateTimes(LocalDateTime.of(2017,4,19,8,0),LocalDateTime.of(2017,4,19,10,0),ADMIN_ID));
    }

    @Test
    public void getAll() throws Exception {
    MATCHER.assertCollectionEquals(Arrays.asList(MEAL2,MEAL3),service.getAll(ADMIN_ID));
    }
    @Test
    public void update() throws Exception {
        Meal meal=new Meal(MEAL_ID,LocalDateTime.of(2018,1,1,1,1),"полдник",2001);
        service.update(meal,USER_ID);
        MATCHER.assertEquals(meal,service.get(MEAL_ID,USER_ID));
    }

    @Test
    public void save() throws Exception {
        Meal meal=new Meal(LocalDateTime.of(2018,1,1,1,1),"полдник",2001);
        service.save(meal,ADMIN_ID);
        meal.setId(MEAL_ID+3);
        MATCHER.assertCollectionEquals(Arrays.asList(meal,MEAL2,MEAL3),service.getAll(ADMIN_ID));
    }
    @Test(expected = NotFoundException.class)
    public void testDelete() throws Exception{
        service.delete(MEAL_ID,ADMIN_ID);
    }
    @Test(expected = NotFoundException.class)
    public void testGet() throws Exception{
        service.get(MEAL_ID,1);
    }
    @Test(expected = NotFoundException.class)
    public void testUpdate() throws Exception{
        Meal meal=new Meal(MEAL_ID,LocalDateTime.of(2018,1,1,1,1),"полдник",2001);
        service.update(meal,ADMIN_ID);
    }
}
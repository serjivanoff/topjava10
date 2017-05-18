package ru.javawebinar.topjava.web.meal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;

public class MealRestControllerTest extends AbstractControllerTest {

@Autowired
    MealService mealService;
    @Test
    public void testGet() throws Exception {
    mockMvc.perform(get(REST_URL+"/"+MEAL1_ID)).andExpect(status().isOk()).andDo(print())
    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
    .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
      mockMvc.perform(get(REST_URL+"/delete/"+MEAL1_ID)).andExpect(status().isOk()).andDo(print());
      MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2),mealService.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)).andExpect(status().isOk()).andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEED.contentListMatcher(MEAL_WITH_EXCEED));
    }

    @Test
    public void testCreate() throws Exception {
        Meal meal=getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)));
//                .andExpect(status().isCreated());
        meal.setId(MEAL1_ID+8);
        MATCHER.assertEquals(meal,MATCHER.fromJsonAction(action));
        MATCHER.assertCollectionEquals(Arrays.asList(meal,MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1)
                ,mealService.getAll(USER_ID));
    }
    @Test
    public void testUpdate() throws Exception {
        Meal meal=getUpdated();
        ResultActions action=mockMvc.perform(post(REST_URL+"/"+MEAL1_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(meal)));
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2,meal)
                ,mealService.getAll(USER_ID));
    }
    @Test
    public void testGetBetween() throws Exception {
        LocalDateTime from=of(2015, Month.MAY, 30, 13, 0);
        LocalDateTime to=of(2015, Month.MAY, 30, 20, 0);
        mockMvc.perform(post("/filter").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(from)).content(JsonUtil.writeValue(to)));
//        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3,MEAL2),MATCHER.));
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3,MEAL2)
       ,mealService.getBetweenDateTimes(from,to,USER_ID));
    }
}
package ru.javawebinar.topjava.web.meal;

import org.hibernate.service.spi.Startable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.time.LocalDate.parse;

@RestController
@RequestMapping(value = "/ajax/meals")

public class MealAjaxController extends AbstractMealController {
    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable("id")int id) {
        super.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }
    @PostMapping
    public Meal create(@RequestParam("dateTime") String dateTime,
                       @RequestParam("description") String description,
                       @RequestParam("calories") int calories){
        Meal created = new Meal(LocalDateTime.parse(dateTime),description,calories);
        return super.create(created);
    }
    @PostMapping("/filter")
    public List<MealWithExceed>filter(@RequestParam("startDate") String startDate,
                                      @RequestParam("endDate")String endDate,
                                      @RequestParam("startTime") String startTime,
                                      @RequestParam("endTime") String endTime){
        System.out.println("--------------------------------------");
    return super.getBetween(parse(startDate),LocalTime.parse(startTime),parse(endDate),LocalTime.parse(endTime));
    }
}
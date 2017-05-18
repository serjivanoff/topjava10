package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";
    @Autowired
    public MealRestController(MealService service){
        super(service);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
    @Override
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }


    @PostMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE)
    @Override
    public void update(@RequestBody Meal meal,@PathVariable("id") int id)
    {System.out.println("-------------------------FUCK YEAH!!!-----------------------------------------");
        super.update(meal, id);
    }

    @PostMapping(value="/filter",produces=MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestBody LocalDateTime start,@RequestBody LocalDateTime end) {
        String startDateTime=start.toString(), endDateTime=end.toString();
        return super.getBetween(parseLocalDate(startDateTime),
                parseLocalTime(startDateTime), parseLocalDate(endDateTime),parseLocalTime(endDateTime));
    }
}
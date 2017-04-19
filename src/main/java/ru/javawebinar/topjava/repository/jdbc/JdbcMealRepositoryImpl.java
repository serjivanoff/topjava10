package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource,JdbcTemplate jdbcTemplate,
                                  NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertMeal=new SimpleJdbcInsert(dataSource).withTableName("meals").usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("user_id",userId).
                addValue("date_time",meal.getDateTime()).
                addValue("description",meal.getDescription()).
                addValue("calories",meal.getCalories()).
                addValue("meal_id",meal.getId());

        if(meal.isNew()){
            Number mealId=insertMeal.executeAndReturnKey(parameters);
            meal.setId(mealId.intValue());
        }
        else
            {int mealId=namedParameterJdbcTemplate.update("UPDATE meals SET user_id=:user_id,date_time=:date_time," +
                    "description=:description,calories=:calories " +
                    "WHERE(id=:meal_id AND user_id=:user_id)",parameters);
                if(mealId==0)return null;
            }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE (id=? AND user_id=?)",id,userId)!=0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal>result=jdbcTemplate.query("SELECT * FROM meals WHERE(id=? AND user_id=?)",ROW_MAPPER,id,userId);
        return DataAccessUtils.singleResult(result);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal>result=new ArrayList<>();
        result=
//        jdbcTemplate.queryForList("SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC",Meal.class,userId);
        jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC",ROW_MAPPER,userId);
        return result;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE(user_id=? AND date_time BETWEEN ? AND ?)",
                ROW_MAPPER,userId,startDate,endDate);
    }
}

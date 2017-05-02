package ru.javawebinar.topjava.repository.datajpa;

import org.hibernate.annotations.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;
    @Autowired
    private CrudUserRepository userRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(),userId)==null) {
            return null;
        }
        User user=userRepository.getOne(userId);
        meal.setUser(user);
        return crudRepository.save(meal);
    }
    @javax.transaction.Transactional
    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserIdIs(id,userId)!=0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findByIdAndUserIdIs(id,userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findAllByUserIdAndDateTimeIsBetweenOrderByDateTimeDesc
                (userId,startDate,endDate);
    }
    public Meal getWithUser(int id,int userId){
        return crudRepository.getWithUser(id,userId);
    }
}

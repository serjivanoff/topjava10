package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    int deleteByIdAndUserIdIs(int id, int userId);

    Meal findByIdAndUserIdIs(int id, int userId);


    List<Meal> findAllByUserIdAndDateTimeIsBetweenOrderByDateTimeDesc
            (int userId, LocalDateTime startDate, LocalDateTime endDate);

    @javax.persistence.OrderBy(value="date_time desc")
    @Query(value = "SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id=:id AND m.user.id=:userId")
     Meal getWithUser(@Param("id") int id, @Param("userId") int userId);

    @javax.persistence.OrderBy(value="date_time desc")
    @Query(value = "SELECT m FROM Meal m JOIN FETCH m.user WHERE m.user.id=?1 AND m.dateTime BETWEEN ?2 AND ?3")
    List<Meal>getBetweenWithUser(int userId,LocalDateTime startDate,LocalDateTime endDate);
}

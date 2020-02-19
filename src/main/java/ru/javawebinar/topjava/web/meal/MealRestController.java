package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create");
        checkNew(meal);
        int userId = SecurityUtil.authUserId();
        return service.create(meal, userId);
    }

    public Meal update(Meal meal) {
        log.info("update");
        int userId = SecurityUtil.authUserId();
        return service.update(meal, userId);
    }

    public void delete(int mealId) {
        log.info("delete");
        int userId = SecurityUtil.authUserId();
        service.delete(mealId, userId);
    }

    public Meal get(int mealId) {
        log.info("get");
        int userId = SecurityUtil.authUserId();
        return service.get(mealId, userId);
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        int userId = SecurityUtil.authUserId();
        int userCaloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        return service.getAll(userId, userCaloriesPerDay);
    }

    public List<MealTo> getBetweenDateTimes(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getBetweenDateTimes");

        int userId = SecurityUtil.authUserId();
        int userCaloriesPerDay = SecurityUtil.authUserCaloriesPerDay();

        if (startTime == null) {
            startTime = LocalTime.MIN;
        }
        if (endTime == null) {
            endTime = LocalTime.MAX;
        }
        if (startDate == null) {
            startDate = LocalDate.of(1900, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.of(3000, 1, 1);
        }

        return service.getBetweenDateTimes(userId, userCaloriesPerDay, startDate, endDate, startTime, endTime);
    }
}
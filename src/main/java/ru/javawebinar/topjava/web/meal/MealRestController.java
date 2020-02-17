package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private MealService service;

    public Meal createUpdate(Meal meal) {
        log.info("createUpdate");
        meal.setUserId(SecurityUtil.authUserId());
        return service.createUpdate(meal);
    }

    public boolean delete(int mealId) {
        log.info("delete");
        int userId = SecurityUtil.authUserId();
        return service.delete(mealId, userId);
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

    public List<MealTo> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        log.info("getBetweenDateTimes");

        int userId = SecurityUtil.authUserId();
        int userCaloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        return service.getBetweenDateTimes(userId, userCaloriesPerDay, startDateTime.toLocalDate(), endDateTime.toLocalDate(),
                startDateTime.toLocalTime(), endDateTime.toLocalTime());
    }
}
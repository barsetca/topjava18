package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceTest {

    static {

        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal getMeal = service.get(USER_MEAL_ID, USER_ID);
        MealTestData.assertMatch(MEAL_USER, getMeal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(USER_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID, USER_ID);
        assertEquals(service.getAll(USER_ID).size(), 0);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(USER_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        Meal meal1 = new Meal(LocalDateTime.of(2015, 2, 25, 10, 0, 0, 0),
                "Завтрак", 500);
        Meal meal2 = new Meal(LocalDateTime.of(2015, 2, 27, 14, 0, 0, 0),
                "Обед", 1000);
        service.create(meal1, USER_ID);
        service.create(meal2, USER_ID);
        List<Meal> listBetween = service.getBetweenDates(LocalDate.of(2015, 2, 25),
                LocalDate.of(2015, 2, 27), USER_ID);
        MealTestData.assertMatch(listBetween, meal2, meal1);
    }

    @Test
    public void getAll() {
        MealTestData.assertMatch(service.getAll(ADMIN_ID), MEAL_ADMIN);
    }

    @Test
    public void update() {
        Meal meal = new Meal(USER_MEAL_ID,
                LocalDateTime.of(2015, 2, 25, 10, 0, 0, 0),
                "Завтрак", 500);
        service.update(meal, USER_ID);
        MealTestData.assertMatch(service.getAll(USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = new Meal(USER_MEAL_ID,
                LocalDateTime.of(2015, 2, 25, 10, 0, 0, 0),
                "Завтрак", 500);
        service.update(meal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "Ужин", 1000);
        service.create(newMeal, USER_ID);
        MealTestData.assertMatch(service.getAll(USER_ID), newMeal, MEAL_USER);
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateDateCreate() {
        Meal newMeal = new Meal(LocalDateTime.of(2020, 2, 25, 10, 0, 0, 0), "Ужин", 1000);
        service.create(newMeal, USER_ID);
        MealTestData.assertMatch(service.getAll(USER_ID), newMeal, MEAL_USER);
    }
}
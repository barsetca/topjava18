package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithListMeals() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
        User user = service.create(newUser);
        User userWithMeal = service.getWithListMeals(user.getId());
        assertMatch(user, userWithMeal);
        User oldUser = service.getWithListMeals(USER_ID);
        MealTestData.assertMatch(oldUser.getMeals(), MealTestData.MEALS);
    }

    @Test
    public void getWithListMealsNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getWithListMeals(1);
    }
}



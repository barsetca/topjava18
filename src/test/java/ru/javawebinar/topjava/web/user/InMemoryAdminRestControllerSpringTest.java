package ru.javawebinar.topjava.web.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration("classpath:spring/spring-app1.xml")
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.init();
    }

    @Test
    public void delete() throws Exception {
        controller.delete(USER_ID);
        Collection<User> users = controller.getAll();
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.iterator().next(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        controller.delete(10);
    }

    @Test
    public void getAll() {
        assertMatch(controller.getAll(), ADMIN, USER);
    }

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555,
                false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = controller.create(newUser);
        newUser.setId(created.getId());
        assertMatch(controller.getAll(), ADMIN, newUser, USER);
    }

    @Test
    public void get() throws Exception {
        User user = controller.get(USER_ID);
        assertMatch(user, USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        controller.get(1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = controller.getByEmail("user@yandex.ru");
        assertMatch(user, USER);
    }

    @Test
    public void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        controller.update(updated, USER_ID);
        assertMatch(controller.get(USER_ID), updated);
    }
}

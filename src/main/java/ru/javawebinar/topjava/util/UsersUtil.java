package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(1, "One", "1@1", "password1", Role.ROLE_USER, Role.ROLE_USER),
            new User(2, "Two", "2@2", "password2", Role.ROLE_ADMIN, Role.ROLE_ADMIN),
            new User(3, "One", "0@3", "password3", Role.ROLE_ADMIN, Role.ROLE_USER, Role.ROLE_ADMIN)
    );
}

/*

 */
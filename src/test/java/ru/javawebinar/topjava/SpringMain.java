package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app1.xml", "spring/spring-app2.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);

            adminUserController.create(new User(null, "User", "user@yandex.ru", "password", Role.ROLE_USER));
            adminUserController.create(new User(null, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "Admin2", "admin2@gmail.com", "admin2", Role.ROLE_ADMIN));
            adminUserController.getAll().forEach(System.out::println);
            System.out.println();
            System.out.println(adminUserController.get(9));
            System.out.println();
            adminUserController.delete(11);
            adminUserController.getAll().forEach(System.out::println);
            System.out.println();
            System.out.println(adminUserController.getByEmail("admin@gmail.com"));
            adminUserController.update(new User(10, "userName", "email@gjhkk", "password", Role.ROLE_ADMIN), 10);
            //adminUserController.update(new User(ADMIN_ID,"userName", "email@gjhkk", "password", Role.ROLE_ADMIN), 4);
            adminUserController.getAll().forEach(System.out::println);
//


            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().forEach(System.out::println);
            System.out.println();

            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 11, 0), "Завтрак", 50));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 29, 11, 0), "Завтрак", 1000));
            System.out.println();
            mealRestController.getAll().forEach(System.out::println);
            System.out.println();

            System.out.println();
            System.out.println(mealRestController.get(13));
            Meal meal = new Meal(13, LocalDateTime.of(2015, Month.MAY, 30, 12, 0), "Полдник", 500);
            mealRestController.update(meal, 13);
            System.out.println(mealRestController.get(13));
            mealRestController.delete(13);
            mealRestController.getAll().forEach(System.out::println);
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 29, 11, 0), "Завтрак", 1000));
            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 28, 11, 0), "Завтрак", 50));
            System.out.println(mealRestController.getBetween(
                    LocalDate.of(2015, 5, 29), LocalTime.MIN,
                    LocalDate.of(2015, 5, 30), LocalTime.MAX));

        }
    }
}

package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {

        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 12, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 12, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 7, 0), "Еда на граничное значение", 100)
        );

        List<UserMealWithExcess> mealsToCycles = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        List<UserMealWithExcess> mealsToStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapDayTotal = new HashMap<>();
        for (UserMeal userMeal : meals) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            mapDayTotal.merge(localDate, userMeal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> listDayExcess = new ArrayList<>();
        boolean excess;
        for (UserMeal userMeal : meals) {

            if (TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                excess = mapDayTotal.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
                listDayExcess.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess));
            }
        }
        return listDayExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals,
                                                             LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapDayTotalCal = new HashMap<>();

        List<UserMeal> listDayExcess = meals.stream()
                .peek(u -> {
                    LocalDate localDate = u.getDateTime().toLocalDate();
                    mapDayTotalCal.merge(localDate, u.getCalories(), Integer::sum);
                })
                .filter(u -> TimeUtil.isBetweenInclusive(u.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());

        boolean[] excess = new boolean[1];

        return listDayExcess.stream()
                .map(u -> {
                    excess[0] = mapDayTotalCal.get(u.getDateTime().toLocalDate()) > caloriesPerDay;
                    return new UserMealWithExcess(u.getDateTime(), u.getDescription(), u.getCalories(), excess[0]);
                }).collect(Collectors.toList());
    }
}

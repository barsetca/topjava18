package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<UserMealWithExcess> mealsTo = filteredByCyclesCheat(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println();

        List<UserMealWithExcess> mealsToStream = filteredByStreamsCheat(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStream.forEach(System.out::println);
        System.out.println();

//        List<UserMealWithExcess> mealsTo1 = filteredByCyclesOne(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo1.forEach(System.out::println);
//        System.out.println();
//
//        List<UserMealWithExcess> mealsTo2 = filteredByCyclesTwo(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo2.forEach(System.out::println);
//        System.out.println();
//
//        List<UserMealWithExcess> mealsTo3 = filteredByCyclesThree(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo3.forEach(System.out::println);
//        System.out.println();
//
//        List<UserMealWithExcess> mealsToStream1 = filteredByStreamsOne(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsToStream1.forEach(System.out::println);
//        System.out.println();
//
//
//        List<UserMealWithExcess> mealsToStream2 = filteredByStreamsTwo(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsToStream1.forEach(System.out::println);
//        System.out.println();
//
//        List<UserMealWithExcess> mealsToStream3 = filteredByStreamsThree(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsToStream1.forEach(System.out::println);
//        System.out.println();
//
    }

    public static List<UserMealWithExcess> filteredByCyclesCheat(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        if (meals == null || meals.size() == 0) {
            System.out.println("List<UserMeal> meals must not be null or empty ");
            return new ArrayList<>();
        }
        List<UserMealWithExcess> listDayExcess = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            if (userMeal == null) {
                System.out.println("object UserMeal into the List<UserMeal> meals not be null");
                continue;
            }
            if (userMeal.getCal(userMeal.getDateTime().toLocalDate()) > caloriesPerDay
                    && TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                listDayExcess.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
            }
        }
        return listDayExcess;
    }

    public static List<UserMealWithExcess> filteredByStreamsCheat(List<UserMeal> meals, LocalTime
            startTime, LocalTime endTime, int caloriesPerDay) {
        try {
            return meals.stream().filter(u -> u.getCal(u.getDateTime().toLocalDate()) > caloriesPerDay)
                    .filter(u -> TimeUtil.isBetweenInclusive(u.getDateTime().toLocalTime(), startTime, endTime))
                    .map(u -> new UserMealWithExcess(u.getDateTime(), u.getDescription(), u.getCalories(), true))
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            System.out.println("List meals must or object UserMeal not be null or empty " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<UserMealWithExcess> filteredByCyclesOne(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        if (meals == null || meals.size() == 0) {
            System.out.println("List meals must not be null or empty");
            return new ArrayList<>();
        }
        Map<LocalDate, Integer> mapDayTotal = new HashMap<>();
        for (UserMeal userMeal : meals) {
            if (userMeal == null) {
                System.out.println("object UserMeal into the List<UserMeal> meals not be null");
                continue;
            }
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            mapDayTotal.merge(localDate, userMeal.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> listDayExcess = new ArrayList<>();
        mapDayTotal.forEach((k, v) -> {
            if (v > caloriesPerDay) {
                for (UserMeal userMeal : meals) {
                    if (userMeal == null) {
                        System.out.println("object UserMeal into the List<UserMeal> meals not be null");
                        continue;
                    }
                    if (userMeal.getDateTime().toLocalDate().isEqual(k)
                            && TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                        listDayExcess.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
                    }
                }
            }
        });
        return listDayExcess;
    }

    public static List<UserMealWithExcess> filteredByCyclesTwo(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        try {
            meals.sort(Comparator.comparing(UserMeal::getDateTime));
            List<UserMealWithExcess> listDayExcess = new ArrayList<>();
            List<UserMeal> tempList = new ArrayList<>();
            LocalDate date = meals.get(0).getDateTime().toLocalDate();
            int cal = 0;
            for (UserMeal userMeal : meals) {
                if (userMeal == null) {
                    System.out.println("object UserMeal into the List<UserMeal> meals not be null");
                    continue;
                }
                if (date.isEqual(userMeal.getDateTime().toLocalDate())) {
                    cal = cal + userMeal.getCalories();
                    if (TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                        tempList.add(userMeal);
                    }

                } else {
                    if (cal > caloriesPerDay) {
                        tempList.forEach(s -> listDayExcess.add(new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(), true)));
                    }
                    tempList.clear();
                    cal = userMeal.getCalories();
                    date = userMeal.getDateTime().toLocalDate();
                    if (TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                        tempList.add(userMeal);
                    }
                }
            }
            if (cal > caloriesPerDay) {
                tempList.forEach(s -> listDayExcess.add(new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(), true)));
                tempList.clear();
            }

            return listDayExcess;
        } catch (NullPointerException e) {
            System.out.println("List meals must or object UserMeal not be null or empty " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<UserMealWithExcess> filteredByCyclesThree(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        if (meals == null || meals.size() == 0) {
            System.out.println("List meals must not be null or empty");
            return new ArrayList<>();
        }
        Map<LocalDate, Integer> mapDayTotalCal = new HashMap<>();
        Map<LocalDate, List<UserMealWithExcess>> mapDayListExcess = new HashMap<>();
        for (UserMeal userMeal : meals) {
            if (userMeal == null) {
                System.out.println("object UserMeal into the List<UserMeal> meals not be null");
                continue;
            }
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            mapDayTotalCal.merge(localDate, userMeal.getCalories(), Integer::sum);
            mapDayListExcess.computeIfAbsent(localDate, v -> new ArrayList<>());
            if (TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExcess userMealWithExcess =
                        new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true);
                mapDayListExcess.get(localDate).add(userMealWithExcess);
            }
        }
        List<UserMealWithExcess> listDayExcess = new ArrayList<>();
        mapDayTotalCal.forEach((k, v) -> {
            if (v > caloriesPerDay) {
                listDayExcess.addAll(mapDayListExcess.get(k));
            }
        });
        return listDayExcess;
    }

    public static List<UserMealWithExcess> filteredByStreamsOne(List<UserMeal> meals, LocalTime
            startTime, LocalTime endTime, int caloriesPerDay) {
        try {
            List<UserMealWithExcess> listDayExcess1 = new ArrayList<>();
            Map<LocalDate, Integer> mapDayTotal = meals.stream()
                    .collect(Collectors.toMap(i -> i.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));
            mapDayTotal.entrySet().stream().filter((p) -> p.getValue() > caloriesPerDay)
                    .map(x -> meals.stream().filter(y -> (y.getDateTime().toLocalDate().isEqual(x.getKey())
                            && TimeUtil.isBetweenInclusive(y.getDateTime().toLocalTime(), startTime, endTime)))
                            .map(f -> listDayExcess1.add(new UserMealWithExcess(f.getDateTime(), f.getDescription(), f.getCalories(), true)))).
                    forEach(Stream::count);
            return listDayExcess1;
        } catch (NullPointerException e) {
            System.out.println("List meals must or object UserMeal not be null or empty " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<UserMealWithExcess> filteredByStreamsTwo(List<UserMeal> meals, LocalTime
            startTime, LocalTime endTime, int caloriesPerDay) {
        try {
            List<UserMealWithExcess> listDayExcess = new ArrayList<>();
            Map<LocalDate, List<UserMeal>> map = meals.stream().collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()));
            map.values().stream().filter(v -> v.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay)
                    .map(v -> v.stream().filter(x -> TimeUtil.isBetweenInclusive(x.getDateTime().toLocalTime(), startTime, endTime))
                            .map(s -> listDayExcess.add(new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(), true))))
                    .forEach(Stream::count);
            return listDayExcess;
        } catch (NullPointerException e) {
            System.out.println("List meals must or object UserMeal not be null or empty " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<UserMealWithExcess> filteredByStreamsThree(List<UserMeal> meals, LocalTime
            startTime, LocalTime endTime, int caloriesPerDay) {
        try {
            Map<LocalDate, Integer> mapDayTotalCal = new HashMap<>();
            List<UserMeal> listDayExcess = meals.stream().peek(u -> {
                LocalDate localDate = u.getDateTime().toLocalDate();
                mapDayTotalCal.merge(localDate, u.getCalories(), Integer::sum);
            })
                    .filter(u -> TimeUtil.isBetweenInclusive(u.getDateTime().toLocalTime(), startTime, endTime))
                    .collect(Collectors.toList());
            return listDayExcess.stream()
                    .filter(s -> mapDayTotalCal.getOrDefault(s.getDateTime().toLocalDate(), 0) > caloriesPerDay)
                    .map(u -> new UserMealWithExcess(u.getDateTime(), u.getDescription(), u.getCalories(), true))
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            System.out.println("List meals must or object UserMeal not be null or empty " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

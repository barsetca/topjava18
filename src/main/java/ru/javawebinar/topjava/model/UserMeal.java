package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserMeal {
    private final LocalDateTime dateTime;

    private final String description;
    private final int calories;
    private static final Map<LocalDate, Integer> mapCalDay = new HashMap<>();

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        Objects.requireNonNull(dateTime, "dateTime must not be null");
        if (description == null) {
            description = "";
        }
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        mapCalDay.merge(dateTime.toLocalDate(), calories, Integer::sum);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public int getCal(LocalDate localDate) {

        return mapCalDay.getOrDefault(localDate, 0);
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}

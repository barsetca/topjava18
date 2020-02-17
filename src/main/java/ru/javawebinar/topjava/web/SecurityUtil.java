package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static String userId;

    public static int authUserId() {
        if ("".equals(userId) || userId == null) {
            userId = "0";
        }
        return Integer.parseInt(userId);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

}
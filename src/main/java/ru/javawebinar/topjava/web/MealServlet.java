package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapStorageMeal;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getFiltered;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private Storage storageMeal = new MapStorageMeal();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime localDateTime = TimeUtil.stringToLocalDateTime(request.getParameter("dateTime"));
        int calories = Integer.parseInt(request.getParameter("calories"));
        String description = request.getParameter("description");
        String action = request.getParameter("action");
        Meal meal;
        if (action.equals("add")) {
            meal = new Meal(localDateTime, description, calories);
            storageMeal.save(meal);
        } else {
            meal = storageMeal.get(id);
            meal.setDateTime(localDateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
            storageMeal.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealTo> mealsToAll = getFiltered(storageMeal.getListAll(), LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY);

        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("mealsTo", mealsToAll);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        String stringId = request.getParameter("id");
        int id = 0;
        if (stringId != null) {
            id = Integer.parseInt(stringId);
        }
        Meal meal;
        switch (action) {
            case "delete":
                storageMeal.delete(id);
                response.sendRedirect("meals");
                return;
            case "edit":
                meal = storageMeal.get(id);
                break;
            case "add":
                meal = new Meal(TimeUtil.NOW_WITHOUT_SECOND_NANO, "", 0);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
    }
}

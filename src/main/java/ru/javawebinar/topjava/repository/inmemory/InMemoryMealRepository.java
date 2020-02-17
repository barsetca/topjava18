package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(MEAL -> {
            MEAL.setUserId(SecurityUtil.authUserId());
            save(MEAL);
        });
        Meal meal = new Meal(8, LocalDateTime.of(2015, Month.MAY, 30, 12, 0), "Полдник", 500);
        meal.setUserId(2);
        save(meal);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal);
        if (meal == null) {
            return null;
        }

        if (meal.isNew()) {
            Integer newId = counter.incrementAndGet();
            meal.setId(newId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        return check(repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal), meal.getUserId());
    }

    @Override
    public boolean delete(int mealId, int userId) {
        return get(mealId, userId) != null && repository.remove(mealId) != null;

    }

    @Override
    public Meal get(int mealId, int userId) {
        Meal getMeal = repository.get(mealId);
        return check(getMeal, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted((o1, o2) -> {
                    int compare = o2.getDateTime().toLocalDate().compareTo(o1.getDateTime().toLocalDate());
                    if (compare == 0) {
                        return o1.getDateTime().toLocalTime().compareTo(o2.getDateTime().toLocalTime());
                    }
                    return compare;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isDateBetween(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    private Meal check(Meal checkMeal, int userId) {
        if (checkMeal == null || checkMeal.getUserId() != userId) {
            return null;
        }
        return checkMeal;
    }
}


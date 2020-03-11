package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaUserRepository implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    @Autowired
    private CrudUserRepository crudRepository;

    @Autowired
    private MealRepository mealRepository;

    @Override
    @Transactional
    public User save(User user) {
        //mealRepository.getAll(user.getId());
        if (user.isNew()) {
            user.setMeals(new ArrayList<>());
        } else {
            int id = user.getId();
            user.setMeals(mealRepository.getAll(user.getId()));
        }
        return crudRepository.save(user);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    @Transactional
    public User getWithListMeals(int id) {
        User user = get(id);
        user.setMeals(mealRepository.getAll(id));
        return user;
    }
}

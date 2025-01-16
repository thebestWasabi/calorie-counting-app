package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(final MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(final int id, final int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(final int userId) {
        return new ArrayList<>(repository.getAll(userId));
    }

    public Meal create(final Meal meal, final int userId) {
        return repository.save(meal, userId);
    }

    public void update(final Meal meal, final int userId) {
        checkNotFoundWithId(repository.get(meal.getId(), userId), meal.getId());
    }

    public void delete(final int id, final int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }
}

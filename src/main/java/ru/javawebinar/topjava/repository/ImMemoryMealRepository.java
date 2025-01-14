package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */
public class ImMemoryMealRepository implements MealRepository {

    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private final static AtomicInteger counter = new AtomicInteger();

    {
        for (final Meal meal : MealsUtil.MEALS) {
            meals.put(meal.getId(), meal);
        }
    }

    @Override
    public Meal get(final int id) {
        return meals.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return Collections.unmodifiableCollection(meals.values());
    }

    @Override
    public Meal save(final Meal meal) {
        int id = meal.getId() == null ? counter.incrementAndGet() : meal.getId();
        Meal newMeal = new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        meals.put(id, newMeal);
        return newMeal;
    }

    @Override
    public boolean delete(final int id) {
        return meals.remove(id) != null;
    }
}

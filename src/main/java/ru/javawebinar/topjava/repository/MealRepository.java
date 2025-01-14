package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */
public interface MealRepository {

    Meal get(int id);

    Collection<Meal> getAll();

    Meal save(Meal meal);

    boolean delete(int id);
}

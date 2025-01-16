package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, SecurityUtil.authUserId()));
        save(new Meal(LocalDateTime.of(2025, Month.JANUARY, 5, 14, 0), "Админ ланч", 520), SecurityUtil.authUserId());
        save(new Meal(LocalDateTime.of(2025, Month.JANUARY, 5, 18, 0), "Админ ужин", 550), SecurityUtil.authUserId());
    }

    @Override
    public Meal save(final Meal meal, final int userId) {
//        final Map<Integer, Meal> meals;
//        if (repository.containsKey(userId)) {
//            meals = repository.get(userId);
//        }
//        else {
//            meals = new ConcurrentHashMap<>();
//            repository.put(userId, meals);
//        }

        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(final int id, final int userId) {
        final Map<Integer, Meal> meals = repository.get(userId);
        return meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, final int userId) {
        final Map<Integer, Meal> meals = repository.get(userId);
        return meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(final int userId) {
        final Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() : meals.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
}


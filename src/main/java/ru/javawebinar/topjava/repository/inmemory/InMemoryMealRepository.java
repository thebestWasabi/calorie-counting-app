package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, SecurityUtil.authUserId()));
        save(new Meal(LocalDateTime.of(2025, Month.JANUARY, 5, 14, 0), "Админ ланч", 520), SecurityUtil.authUserId());
        save(new Meal(LocalDateTime.of(2025, Month.JANUARY, 5, 18, 0), "Админ ужин", 550), SecurityUtil.authUserId());
    }

    @Override
    public Meal save(final Meal meal, final int userId) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, uId -> new ConcurrentHashMap<>());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            log.info("Added new meal: {}", meal);
            return meal;
        }

        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            log.info("Updated existing meal: {}", meal);
            return meal;
        });
    }

    @Override
    public boolean delete(final int id, final int userId) {
        final Map<Integer, Meal> meals = repository.get(userId);
        final boolean isDeleted = meals != null && meals.remove(id) != null;

        if (!isDeleted) {
            log.warn("Meal with id {} for user {} not found for deletion", id, userId);
        }
        return isDeleted;
    }

    @Override
    public Meal get(int id, final int userId) {
        final Map<Integer, Meal> meals = repository.get(userId);
        final Meal meal = meals == null ? null : meals.get(id);

        if (meal == null) {
            log.warn("Meal with id {} for user {} not found", id, userId);
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(final int userId) {
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(final LocalDateTime startDateTime, final LocalDateTime endDateTime, final int userId) {
        return getAllFiltered(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> getAllFiltered(final int userId, final Predicate<Meal> predicate) {
        final Map<Integer, Meal> meals = repository.get(userId);

        if (meals == null || meals.isEmpty()) {
            log.warn("No meals found for user {}", userId);
            return Collections.emptyList();
        }

        return meals.values().stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
}

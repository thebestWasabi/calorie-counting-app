package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */

@Controller
public class MealRestController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final MealService mealService;

    @Autowired
    public MealRestController(final MealService mealService) {
        this.mealService = mealService;
    }

    public Meal get(final int id) {
        final int userId = SecurityUtil.authUserId();
        logger.info("Get meal: {} for user: {}", id, userId);
        return mealService.get(id, userId);
    }

    public List<MealTo> getAll() {
        final int userId = SecurityUtil.authUserId();
        logger.info("Get all meals for user: {}", userId);
        return MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getBetweenHalfOpen(@Nullable final LocalDate startDate, @Nullable final LocalTime startTime,
                                           @Nullable final LocalDate endDate, @Nullable final LocalTime endTime)
    {
        final int userId = SecurityUtil.authUserId();
        logger.info("Get between dates({} - {}) time({} - {}) for user: {}", startDate, endDate, startTime, endTime, userId);

        final List<Meal> mealsDateFiltered = mealService.getBetweenHalfOpen(userId, startDate, endDate);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal create(final Meal meal) {
        final int userId = SecurityUtil.authUserId();
        checkNew(meal);
        logger.info("Create meal: {} for user: {}", meal, userId);
        return mealService.create(meal, userId);
    }

    public void update(final Meal meal, final int id) {
        final int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        logger.info("Update meal: {} for user: {}", meal, userId);
        mealService.update(meal, userId);
    }

    public void delete(final int id) {
        final int userId = SecurityUtil.authUserId();
        logger.info("Delete meal: {} for user: {}", id, userId);
        mealService.delete(id, userId);
    }
}

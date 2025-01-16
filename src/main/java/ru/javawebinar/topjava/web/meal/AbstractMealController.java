package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */
public abstract class AbstractMealController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService mealService;

    public List<Meal> getAll(final int userId) {
        logger.info("Get all meals");
        return mealService.getAll(userId);
    }

    public Meal get(final int id, final int userId) {
        logger.info("Get meal by id: {}", id);
        return mealService.get(id, userId);
    }

    public Meal create(final Meal meal, final int userId) {
        logger.info("Create meal: {}", meal);
        checkNew(meal);
        return mealService.create(meal, userId);
    }

    public void update(final Meal meal, final int id, final int userId) {
        logger.info("Update meal: {}", meal);
        assureIdConsistent(meal, id);
        mealService.update(meal, userId);
    }

    public void delete(final int id, final int userId) {
        logger.info("Delete meal: {}", id);
        mealService.delete(id, userId);
    }
}

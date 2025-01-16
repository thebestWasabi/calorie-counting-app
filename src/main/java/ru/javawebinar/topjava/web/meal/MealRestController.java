package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

@Controller
public class MealRestController extends AbstractMealController {

    @Override
    public Meal get(final int id, final int userId) {
        return super.get(id, userId);
    }

    @Override
    public List<Meal> getAll(final int userId) {
        return super.getAll(userId);
    }

    @Override
    public Meal create(final Meal meal, final int userId) {
        return super.create(meal, userId);
    }

    @Override
    public void update(final Meal meal, final int id, final int userId) {
        super.update(meal, id, userId);
    }

    @Override
    public void delete(final int id, final int userId) {
        super.delete(id, userId);
    }
}
package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */
public class MealServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final List<Meal> meals = MealsUtil.MEALS;
        final List<MealTo> mealsDto = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);

        req.setAttribute("meals", mealsDto);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}

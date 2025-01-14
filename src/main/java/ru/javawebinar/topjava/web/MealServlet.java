package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.repository.ImMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */
public class MealServlet extends HttpServlet {

    private MealRepository mealRepository;

    @Override
    public void init() throws ServletException {
        mealRepository = new ImMemoryMealRepository();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("meals", MealsUtil.toTos(mealRepository.getAll(), MealsUtil.DEFAULT_CALORIES));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}

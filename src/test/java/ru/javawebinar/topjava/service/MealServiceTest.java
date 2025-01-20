package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEAL_ID1;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.meal1;
import static ru.javawebinar.topjava.MealTestData.meal2;
import static ru.javawebinar.topjava.MealTestData.meal3;
import static ru.javawebinar.topjava.MealTestData.meal4;
import static ru.javawebinar.topjava.MealTestData.meal5;
import static ru.javawebinar.topjava.MealTestData.meal6;
import static ru.javawebinar.topjava.MealTestData.meal7;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Test
    public void get() {
        final Meal meal = mealService.get(MEAL_ID1, USER_ID);
        MEAL_MATCHER.assertMatch(meal, meal1);
    }

    @Test
    public void getAll() {
        final List<Meal> all = mealService.getAll(USER_ID);
        MEAL_MATCHER.assertMatch(all, meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    public void getBetweenInclusive() {
    }

    @Test
    public void create() {
        Meal created = mealService.create(MealTestData.getNew(), USER_ID);
        final Integer newId = created.getId();
        final Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(newId, USER_ID), newMeal);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}
package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */
public class MealTestData {

    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator();

    public static final int MEAL_ID1 = AbstractBaseEntity.START_SEQ + 3;
    public static final int MEAL_ID2 = AbstractBaseEntity.START_SEQ + 4;
    public static final int MEAL_ID3 = AbstractBaseEntity.START_SEQ + 5;
    public static final int MEAL_ID4 = AbstractBaseEntity.START_SEQ + 6;
    public static final int MEAL_ID5 = AbstractBaseEntity.START_SEQ + 7;
    public static final int MEAL_ID6 = AbstractBaseEntity.START_SEQ + 8;
    public static final int MEAL_ID7 = AbstractBaseEntity.START_SEQ + 9;
    public static final int MEAL_ID8 = AbstractBaseEntity.START_SEQ + 10;
    public static final int MEAL_ID9 = AbstractBaseEntity.START_SEQ + 11;

    public static final Meal meal1 = new Meal(MEAL_ID1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL_ID2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL_ID3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL_ID4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL_ID5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0, 0), "Завтрак", 500);
    public static final Meal meal6 = new Meal(MEAL_ID6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0, 0), "Обед", 1000);
    public static final Meal meal7 = new Meal(MEAL_ID7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0, 0), "Ужин", 510);
    public static final Meal meal8 = new Meal(MEAL_ID8, LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0, 0), "Админ ланч", 510);
    public static final Meal meal9 = new Meal(MEAL_ID9, LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0, 0), "Админ ужин", 1500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2025, Month.JANUARY, 5, 11, 0, 0), "Новая еда", 480);
    }
}

package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    public JdbcMealRepository(final JdbcTemplate jdbcTemplate,
                              final NamedParameterJdbcTemplate namedParameterJdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meal")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        final MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", meal.getId());
        map.addValue("dateTime", meal.getDateTime());
        map.addValue("description", meal.getDescription());
        map.addValue("calories", meal.getCalories());
        map.addValue("userId", userId);

        if (meal.isNew()) {
            final Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        }
        else if (namedParameterJdbcTemplate.update(
                "UPDATE meal " +
                "SET date_time=:dateTime, description=:description, calories=:calories, user_id=:userId " +
                "WHERE id=:id", map) == 0) {
            return null;
        }

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meal WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        final List<Meal> meals = jdbcTemplate.query("SELECT * FROM meal WHERE id=? AND user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meal WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meal " +
                "WHERE user_id=? AND date_time>=? AND date_time<? " +
                "ORDER BY date_time DESC ", ROW_MAPPER, userId, startDateTime, endDateTime);
    }
}

package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.Profiles.HSQL_DB;
import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

public abstract class JdbcMealRepository implements MealRepository {

    private static final RowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    protected final JdbcTemplate jdbcTemplate;

    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected final SimpleJdbcInsert insertMeal;


    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meal")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Repository
    @Profile(POSTGRES_DB)
    public static class PostgresRealization extends JdbcMealRepository {

        public PostgresRealization(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            super(jdbcTemplate, namedParameterJdbcTemplate);
        }

        @Override
        public Meal save(Meal meal, int userId) {
            MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("id", meal.getId())
                    .addValue("description", meal.getDescription())
                    .addValue("calories", meal.getCalories())
                    .addValue("date_time", meal.getDateTime())
                    .addValue("user_id", userId);

            if (meal.isNew()) {
                Number newId = insertMeal.executeAndReturnKey(map);
                meal.setId(newId.intValue());
            } else {
                if (namedParameterJdbcTemplate.update("" +
                        "UPDATE meal " +
                        "   SET description=:description, calories=:calories, date_time=:date_time " +
                        " WHERE id=:id AND user_id=:user_id", map) == 0) {
                    return null;
                }
            }
            return meal;
        }

        @Override
        public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
            return jdbcTemplate.query(
                    "SELECT * FROM meal WHERE user_id=?  AND date_time >=  ? AND date_time < ? ORDER BY date_time DESC",
                    ROW_MAPPER, userId, startDateTime, endDateTime);
        }
    }

    @Repository
    @Profile(HSQL_DB)
    public static class HsqlDbRealization extends JdbcMealRepository {

        public HsqlDbRealization(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            super(jdbcTemplate, namedParameterJdbcTemplate);
        }

        @Override
        public Meal save(Meal meal, int userId) {
            MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("id", meal.getId())
                    .addValue("description", meal.getDescription())
                    .addValue("calories", meal.getCalories())
                    .addValue("date_time", Timestamp.valueOf(meal.getDateTime()))
                    .addValue("user_id", userId);

            if (meal.isNew()) {
                Number newId = insertMeal.executeAndReturnKey(map);
                meal.setId(newId.intValue());
            } else {
                if (namedParameterJdbcTemplate.update("" +
                        "UPDATE meal " +
                        "   SET description=:description, calories=:calories, date_time=:date_time " +
                        " WHERE id=:id AND user_id=:user_id", map) == 0) {
                    return null;
                }
            }
            return meal;
        }

        @Override
        public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
            return jdbcTemplate.query(
                    "SELECT * FROM meal WHERE user_id=?  AND date_time >=  ? AND date_time < ? ORDER BY date_time DESC",
                    ROW_MAPPER, userId, Timestamp.valueOf(startDateTime), Timestamp.valueOf(endDateTime));
        }
    }

    @Override
    public abstract Meal save(Meal meal, int userId);

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meal WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query(
                "SELECT * FROM meal WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meal WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public abstract List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}

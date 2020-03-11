package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.getEndExclusive;
import static ru.javawebinar.topjava.util.DateTimeUtil.getStartInclusive;

@Repository
@Profile(Profiles.POSTGRES_DB)
public class JdbcMealRepositoryPostgres extends JdbcMealRepository {

    public JdbcMealRepositoryPostgres(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected void addDateParamToMapSqlParameterSource(MapSqlParameterSource map, Meal meal) {
        map.addValue("date_time", meal.getDateTime());
    }

    @Override
    protected LocalDateTime convertEndDate(LocalDate endDate) {
        return getEndExclusive(endDate);
    }

    @Override
    protected LocalDateTime convertStartDate(LocalDate startDate) {
        return getStartInclusive(startDate);
    }

}
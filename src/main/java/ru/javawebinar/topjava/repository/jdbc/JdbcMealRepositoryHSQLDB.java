package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import java.sql.Timestamp;
import java.time.LocalDate;

import static ru.javawebinar.topjava.util.DateTimeUtil.getEndExclusive;
import static ru.javawebinar.topjava.util.DateTimeUtil.getStartInclusive;

@Repository
@Profile(Profiles.HSQL_DB)
public class JdbcMealRepositoryHSQLDB extends JdbcMealRepository {

    public JdbcMealRepositoryHSQLDB(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }


    @Override
    protected void addDateParamToMapSqlParameterSource(MapSqlParameterSource map, Meal meal) {
        map.addValue("date_time", Timestamp.valueOf(meal.getDateTime()));
    }


    @Override
    protected Timestamp convertEndDate(LocalDate endDate) {
        return Timestamp.valueOf(getEndExclusive(endDate));
    }

    @Override
    protected Timestamp convertStartDate(LocalDate startDate) {
        return Timestamp.valueOf(getStartInclusive(startDate));
    }
}

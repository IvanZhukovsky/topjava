package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;

@Repository
@Profile("hsqldb")
public class HsqlJdbcMealRepository extends JdbcMealRepository{
    public HsqlJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }
    @Override
    String parseDateTimeForDb(LocalDateTime localDateTime) {
        return DateTimeUtil.toStringWithSeconds(localDateTime);
    }
}

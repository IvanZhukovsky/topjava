package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.DateTimeFilter;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;
    private DateTimeFilter dateTimeFilter;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public MealRestController() {
        this.dateTimeFilter = new DateTimeFilter(null, null, LocalTime.MIN, LocalTime.MAX);
    }

    public void setDateTimeFilter(DateTimeFilter dateTimeFilter) {
        this.dateTimeFilter = dateTimeFilter;
    }

    public DateTimeFilter getDateTimeFilter() {
        return dateTimeFilter;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllFiltered() {
        log.info("getAllFiltered");

        List<MealTo> mealTos = MealsUtil.getFilteredTos(service.getAll(SecurityUtil.authUserId()),
                MealsUtil.DEFAULT_CALORIES_PER_DAY,
                dateTimeFilter.getStartTime() == null ? LocalTime.MIN : dateTimeFilter.getStartTime(),
                dateTimeFilter.getEndTime() == null ? LocalTime.MAX : dateTimeFilter.getEndTime());

        return mealTos.stream()
                .filter(mealTo -> DateTimeUtil.isBetweenHalfOpenDate(mealTo.getDate(),
                        dateTimeFilter.getStartDate() == null ? LocalDate.MIN : dateTimeFilter.getStartDate(),
                        dateTimeFilter.getEndDate() == null ? LocalDate.MAX : dateTimeFilter.getEndDate() ))
                .collect(Collectors.toList());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(SecurityUtil.authUserId(), id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete id = {}, userId = {} ", id, SecurityUtil.authUserId());
        service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal) {
        log.info("update {} with id={} with userId = {}", meal, meal.getId(), SecurityUtil.authUserId());
        assureIdConsistent(meal, meal.getId());
        service.update(SecurityUtil.authUserId(), meal);
    }
}
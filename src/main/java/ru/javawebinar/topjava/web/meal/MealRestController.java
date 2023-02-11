package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Meal get(Integer userId, int id) {
        log.info("get {}", id);
        return service.get(userId, id);
    }

    public Meal create(Integer userId, Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void delete(Integer userId, int id) {
        log.info("delete id = {}, userId = {} ", id, userId);
        service.delete(userId, id);
    }

    public void update(Integer userId, Meal meal) {
        log.info("update {} with id={} with userId = {}" , meal, meal.getId(), userId);
        assureIdConsistent(meal, meal.getId());
        service.update(userId, meal);
    }

}
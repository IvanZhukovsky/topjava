package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealStorage implements MealStorage {

    private final AtomicInteger countId = new AtomicInteger(-1);

    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    {
        List<Meal> meals = new ArrayList<>(Arrays.asList(
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
        new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)));
        for (Meal meal: meals) {
            this.create(meal);
        }
    }

    @Override
    public Meal update(Meal meal) {
        if (storage.containsKey(meal.getId())) {
            storage.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public Meal create(Meal meal) {
        if (meal.getId() == null) {
            getNextId();
            meal = new Meal(countId.get(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
            storage.put(countId.get(), meal);
        }
        return meal;
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    private void getNextId() {
        countId.incrementAndGet();
    }
}

package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMapMealStorage implements MealStorage {

    private int countId = -1;

    private final ConcurrentHashMap<Integer, Meal> storage= new ConcurrentHashMap<>();

    {
        this.create(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        this.create(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        this.create(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        this.create(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        this.create(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        this.create(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        this.create(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    }

    @Override
    public synchronized Meal update(int id, LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(id, dateTime, description, calories);
        storage.put(id, meal);
        return meal;
    }

    @Override
    public Meal create(LocalDateTime dateTime, String description, int calories) {
        int id = getNextId();
        Meal meal = new Meal(id, dateTime, description, calories);
        storage.put(id, meal);
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

    private synchronized int getNextId() {
        AtomicInteger atomicInteger = new AtomicInteger(countId);
        countId = atomicInteger.incrementAndGet();
        return countId;
    }

}

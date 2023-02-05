package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealStorage implements MealStorage {

    private int countId = -1;

    private final CopyOnWriteArrayList<Meal> storage = new CopyOnWriteArrayList<>();

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
        int index = 0;
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId() == id) {
                index = i;
                Meal meal = new Meal(id, dateTime, description, calories);
                storage.set(index, meal);
                return meal;
            }
        }
        return null;
    }

    @Override
    public Meal create(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(getNextId(), dateTime, description, calories);
        storage.add(meal);
        return meal;
    }

    @Override
    public Meal get(int id) {
        Optional<Meal> mealOptional = storage.stream().filter(meal -> Objects.equals(meal.getId(), id)).findFirst();
        return mealOptional.orElse(null);
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId() == id) {
                storage.remove(i);
                break;
            }
        }
    }

    @Override
    public CopyOnWriteArrayList<Meal> getAll() {
        return new CopyOnWriteArrayList<>(storage);
    }

    private synchronized int getNextId() {
        AtomicInteger atomicInteger = new AtomicInteger(countId);
        countId = atomicInteger.incrementAndGet();
        return countId;
    }

}

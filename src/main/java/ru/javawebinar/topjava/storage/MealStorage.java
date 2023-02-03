package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {

    void update(Meal meal);

    void create(Meal meal);

    Meal get(String uuid);

    void delete(String uuid);

    List<Meal> getAll();
}

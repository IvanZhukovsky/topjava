package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;

public class MemoryMealStorage implements MealStorage{

    private final List<Meal> mealStorage;

    public MemoryMealStorage() {
        this.mealStorage = MealsUtil.getDefaultMeals();
    }

    @Override
    public void clear() {

    }

    @Override
    public void update(Meal meal) {

    }

    @Override
    public void save(Meal meal) {
        mealStorage.add(meal);
    }

    @Override
    public Meal get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public List<Meal> getAll() {
        return mealStorage;
    }

    @Override
    public int size() {
        return 0;
    }
}

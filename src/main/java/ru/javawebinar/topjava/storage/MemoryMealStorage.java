package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class MemoryMealStorage implements MealStorage{

    private static Integer countId = -1;

    private final List<Meal> mealStorage = new ArrayList<>(Arrays.asList(
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    ));

    @Override
    public void update(Integer id, LocalDateTime dateTime, String description, int calories) {
        int index = 0;
        for (int i = 0; i < mealStorage.size(); i++) {
            if (Objects.equals(mealStorage.get(i).getId(), id)) {
                index = i;
            }
        }
        mealStorage.set(index, new Meal(id, dateTime, description, calories));
    }

    @Override
    public void create(LocalDateTime dateTime, String description, int calories) {
        mealStorage.add(new Meal(getNextId(), dateTime, description, calories));
    }

    @Override
    public Meal get(Integer id) {
        Optional<Meal> mealOptional= mealStorage.stream().filter(meal -> Objects.equals(meal.getId(), id)).findFirst();
        return mealOptional.isPresent() ? mealOptional.get() : null;
    }

    @Override
    public void delete(Integer id) {
        mealStorage.removeIf(meal -> Objects.equals(meal.getId(), id));
    }

    @Override
    public List<Meal> getAll() {
        return mealStorage;
    }

    public static synchronized Integer  getNextId(){
        countId++;
        return countId;
    }

}

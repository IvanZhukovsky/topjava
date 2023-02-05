package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.List;

public interface MealStorage {

    Meal update(int id, LocalDateTime dateTime, String description, int calories);

    Meal create(LocalDateTime dateTime, String description, int calories);

    Meal get(int id);

    void delete(int id);

    List<Meal> getAll();
}

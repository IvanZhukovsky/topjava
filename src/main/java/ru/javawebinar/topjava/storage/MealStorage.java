package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.List;

public interface MealStorage {

    void update(Integer id, LocalDateTime dateTime, String description, int calories);

    void create(LocalDateTime dateTime, String description, int calories);

    Meal get(Integer id);

    void delete(Integer id);

    List<Meal> getAll();
}

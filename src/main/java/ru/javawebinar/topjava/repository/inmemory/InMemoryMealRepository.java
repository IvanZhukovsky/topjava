package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        for (int i = 0; i < MealsUtil.meals.size(); i++) {
            if (i < MealsUtil.meals.size() / 2) {
                this.save(1, MealsUtil.meals.get(i));
            } else {
                this.save(2, MealsUtil.meals.get(i));
            }
        }
        //MealsUtil.meals.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
            return !isBelongUser(meal.getId(), userId) ? null : repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal delete(int userId, int id) {
        if (repository.get(id) != null) {
            return !isBelongUser(id, userId) ? null : repository.remove(id);
        } else return null;
    }

    @Override
    public Meal get(int userId, int id) {
        if (repository.get(id) != null) {
            Meal meal = repository.get(id);
            return !isBelongUser(meal, userId) ? null : meal;
        } else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private boolean isBelongUser(int id, int userId) {
        if (repository.get(id) == null) return false;
        return repository.get(id).getUserId().equals(userId);
    }

    private boolean isBelongUser(Meal meal, Integer userId) {
        return meal.getUserId().equals(userId);
    }

}


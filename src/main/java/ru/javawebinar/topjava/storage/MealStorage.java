package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealStorage {

    Meal save(Meal meal);

    Meal get(int id);

    boolean delete(int id);

    Collection<Meal> getAll();
}

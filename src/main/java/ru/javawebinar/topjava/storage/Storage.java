package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Collection;
import java.util.List;

public interface Storage {

    Meal save(Meal meal);

    Meal get(int id);

    boolean delete(int id);

    Collection<Meal> getAll();
}

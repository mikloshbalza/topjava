package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() {
        User adminUser = service.getWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(adminUser, UserTestData.admin);
        MEAL_MATCHER.assertMatch(adminUser.getMeals(), MealTestData.adminMeals);
    }

    @Test
    public void getWithMealsNotFound() {
        assertThrows(NotFoundException.class, () -> service.getWithMeals(UserTestData.NOT_FOUND));
    }

    @Test
    public void getWithMealsNoMeals() {
        User guestUser = service.getWithMeals(GUEST_ID);
        User userNoMeals = new User();
        List<Meal> mealsEmpty = new ArrayList<>();
        userNoMeals.setMeals(mealsEmpty);
        MEAL_MATCHER.assertMatch(guestUser.getMeals(), userNoMeals.getMeals());
    }
}

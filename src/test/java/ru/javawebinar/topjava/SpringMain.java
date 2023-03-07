package ru.javawebinar.topjava;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;


public class SpringMain {

    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        GenericApplicationContext context = new GenericApplicationContext();
        context.getEnvironment().setActiveProfiles("postgres", "datajpa");
        new XmlBeanDefinitionReader(context).loadBeanDefinitions("spring/spring-app.xml", "spring/spring-db.xml");
        context.refresh();

        System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));
        AdminRestController adminUserController = context.getBean(AdminRestController.class);
        adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
        System.out.println();

        UserService userService = context.getBean(UserService.class);
        User user  = userService.get(100000);

        //System.out.println(user.getMeals());

        MealService mealService = context.getBean(MealService.class);
        MealRestController mealController = context.getBean(MealRestController.class);
        Meal meal = mealService.get(100005, 100000);
        System.out.println(meal.getUser());
        List<MealTo> filteredMealsWithExcess =
                mealController.getBetween(
                        LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
                        LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
        filteredMealsWithExcess.forEach(System.out::println);
        System.out.println();
        System.out.println(mealController.getBetween(null, null, null, null));
    }
}

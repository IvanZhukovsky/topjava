package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private MealStorage mealStorage;
    private final int CALORIES_PER_DAY = 2000;
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealStorage = new MemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealStorage.getAll(), LocalTime.MIN, LocalTime.MAX,
                    CALORIES_PER_DAY));
            log.debug("forward to meals");
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        Meal meal = null;
        switch (action) {
            case "delete":
                log.debug("сработала команда на удаление от id = " + id );
                mealStorage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                return;
            case "edit":
                if (id.equals("new")) {
                    log.debug(id);
                    //meal = new Meal(LocalDateTime.now(), "", 0);
                } else {
                    meal = mealStorage.get(Integer.parseInt(id));
                }
                break;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        if (id.isEmpty()) {
            mealStorage.create(parseDate(dateTime), description, Integer.parseInt(calories));
        } else {
            mealStorage.update(Integer.parseInt(id), parseDate(dateTime), description, Integer.parseInt(calories));
        }
        log.debug("id= " + id);
        log.debug(dateTime);
        log.debug(description);
        log.debug(calories);

        response.sendRedirect("meals");
    }

    private LocalDateTime parseDate(String dateTime) {
        String dateTimePattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
        return LocalDateTime.parse(dateTime.replace("T", " "), dateTimeFormatter);
    }
}

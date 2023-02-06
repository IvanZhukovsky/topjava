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

    private static final Logger log = getLogger(MealServlet.class);
    static final int CALORIES_PER_DAY = 2000;

    private MealStorage mealStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealStorage = new MemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealStorage.getAll(), LocalTime.MIN, LocalTime.MAX,
                    CALORIES_PER_DAY));
            log.debug("forward to meals");
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete": {
                String deleteId = request.getParameter("id");
                mealStorage.delete(Integer.parseInt(deleteId));
                log.debug("delete meal with id {}", deleteId);
                response.sendRedirect("meals");
                return;
            }
            case "edit": {
                String editId = request.getParameter("id");
                Meal meal = mealStorage.get(Integer.parseInt(editId));
                log.debug("edit meal with id {}", editId);
                request.setAttribute("meal", meal);
            }
            break;
            case "new":
                log.debug("add form opened");
                break;
            default:
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealStorage.getAll(), LocalTime.MIN, LocalTime.MAX,
                        CALORIES_PER_DAY));
                request.getRequestDispatcher("meals.jsp").forward(request, response);

        }
        LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);
        now.format(DateTimeFormatter.ISO_DATE_TIME);
        request.setAttribute("now", now);
        request.getRequestDispatcher("editMeal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        if (id.isEmpty()) {
            mealStorage.create(new Meal(parseDate(dateTime), description, Integer.parseInt(calories)));
            log.debug("Add new meal with date&time {} description {} calories {}", dateTime, description, calories);
        } else {
            mealStorage.update(new Meal(Integer.parseInt(id), parseDate(dateTime), description, Integer.parseInt(calories)));
            log.debug("Edit new meal with id {} date&time {} description {} calories {}", id, dateTime, description, calories);
        }
        response.sendRedirect("meals");
    }

    private LocalDateTime parseDate(String dateTime) {
        return LocalDateTime.parse(dateTime).withSecond(0).withNano(0);
    }
}

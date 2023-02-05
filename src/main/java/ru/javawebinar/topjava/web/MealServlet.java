package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.MemoryMapMealStorage;
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
    private MealStorage mealStorage;
    static final int CALORIES_PER_DAY = 2000;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //mealStorage = new MemoryMealStorage();
        mealStorage = new MemoryMapMealStorage();
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
        String header = "";
        switch (action) {
            case "delete":
                String id = request.getParameter("id");
                mealStorage.delete(Integer.parseInt(id));
                log.debug("delete meal with id {}", id);
                response.sendRedirect("meals");
                return;
            case "edit":
                header = "Edit meal";
                id = request.getParameter("id");
                Meal meal = mealStorage.get(Integer.parseInt(id));
                log.debug("edit meal with id {}", id);
                request.setAttribute("meal", meal);
                break;
            case "new":
                header = "Add meal";
                log.debug("add form opened");
                break;
            default:
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);
        now.format(DateTimeFormatter.ISO_DATE_TIME);
        request.setAttribute("now", now);
        request.setAttribute("head", header);
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
            log.debug("Add new meal with date&time {} description {} calories {}", dateTime, description, calories);
        } else {
            mealStorage.update(Integer.parseInt(id), parseDate(dateTime), description, Integer.parseInt(calories));
            log.debug("Edit new meal with id {} date&time {} description {} calories {}", id, dateTime, description, calories);
        }
        response.sendRedirect("meals");
    }

    private LocalDateTime parseDate(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME).withSecond(0).withNano(0);
    }
}

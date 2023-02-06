<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">

        <h2>${meal == null ? "Add meal" : "Edit meal"}</h2>

        <input type="hidden" name="id" value="${meal.id}">
        <table>
            <tr>
                <td>Date Time:</td>
                <td><input type="datetime-local" name="dateTime" value="${meal == null ? now : meal.getDateTime()}"></td>
            </tr>
            <tr>
                <td width="150">Description:</td>
                <td><input type="text" name="description" value="${meal.description}"></td>
            </tr>
            <tr>
                <td>Calories:</td>
                <td><input type="number" name="calories" value="${meal.calories}"></td>
            </tr>
        </table>
        <button type="submit">Save</button>
        <button type="reset" onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>

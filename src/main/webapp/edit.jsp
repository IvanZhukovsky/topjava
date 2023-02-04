<%--
  Created by IntelliJ IDEA.
  User: zhukovsky
  Date: 03.02.2023
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <h2>Edit meal</h2>
        <input type="hidden" name="id" value="${meal.getId()}">
        <table>
            <tr>
                <td>Date Time:</td>
                <td><input type="datetime-local" name="dateTime" value="${meal.getDateTime()}"></td>
            </tr>
            <tr>
                <td width="150">Description:</td>
                <td><input type="text" name="description" value="${meal.getDescription()}"></td>
            </tr>
            <tr>
                <td>Calorites:</td>
                <td><input type="text" name="calories" value="${meal.getCalories()}"></td>
            </tr>
        </table>
        <button type="submit">Save</button>
        <button type="reset" onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>

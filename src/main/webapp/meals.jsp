<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>

<style type="text/css">
    td, th {border: 1px solid black;}
</style>

<body>
<h3><a href="index.html">Home</a></h3>
<a href="meals?id=${"new"}&action=new">Add Meal</a>
<table>
    <tr>
        <th>id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo "/>
        <tr style="color: ${mealTo.excess ? 'red;' : 'green;'}" >
            <td>${mealTo.id}</td>
            <td>${mealTo.getDate()}  ${mealTo.getTime()}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?id=${mealTo.id}&action=edit">update</a></td>
            <td><a href="meals?id=${mealTo.id}&action=delete">delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
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
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo "/>
        <%if (mealTo.isExcess()) {%>
        <tr style="color:red;">
        <%} else {%>
        <tr style="color:green;">
        <%}%>
            <td><%=mealTo.getDate()%> <%=mealTo.getTime()%></td>
            <td><%=mealTo.getDescription()%></td>
            <td><%=mealTo.getCalories()%></td>
            <td><a href="">update</a></td>
            <td><a href="">delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
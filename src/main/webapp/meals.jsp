<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3>Статистика приёма пищи</h3>
<table border="1" cellpadding="8" cellspacing="0">

    <tr bgcolor="#87cefa">
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>

    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>

        <c:if test="${mealTo.excess==true}">
            <tr>
                <td align="center"><font color="red"><%=TimeUtil.localTimeToString(mealTo.getDateTime())%>
                </font></td>
                <td align="center"><font color="red">${mealTo.description}</font></td>
                <td align="center"><font color="red">${mealTo.calories}</font></td>
            </tr>
        </c:if>

        <c:if test="${mealTo.excess==false}">
            <tr>
                <td align="center"><font color="green"><%=TimeUtil.localTimeToString(mealTo.getDateTime())%>
                </font></td>
                <td align="center"><font color="green">${mealTo.description}</font></td>
                <td align="center"><font color="green">${mealTo.calories}</font></td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>
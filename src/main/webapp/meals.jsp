<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Title</title>
    <style type="text/css">

        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }
        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            /*color:green;*/
            border-color: #ccc;
            background-color: #fff;
        }
        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }
        .exceeded {
            color:red;
        }
        .normal {
           color:green;
        }
          </style>
</head>
<body>
<h2>here is meal</h2>

<table class="tg">
    <tr>
        <th width="40">ID</th>
        <th width="120"> Date & Time</th>
        <th width="120">Description</th>
        <th width="50">Calories</th>
        <th width="50">Edit/Delete</th>
    </tr>
    <c:forEach var="meal" items="${mealList}">
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <td>${meal.id}</td>
            <td>${meal.getDateTime()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?id=${meal.id}" >Delete</a></td>
        </tr>
    </c:forEach>
</table>
<h2>
    <pre>                   ADD MEAL</pre>
</h2>
<form name="createOrUpdate" method="post" action="meals">
    ID
    <input type="text" name="id">
    DateTime
    <input type="datetime" name="date">
    Description
    <input type="text" name="descript">
    Calories
    <input type="text" name="cals">
    <input type="submit" name="add" value="Update/Create">
</form>
</body>
</html>

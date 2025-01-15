<%@ page contentType="text/html;charset=UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Meal</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>

<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.cmd == 'create' ? 'Create meal' : 'Edit meal'}</h2>

<%--    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>--%>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><label for="dateTime">DateTime:</label></dt>
            <dd><input type="datetime-local" id="dateTime" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><label for="description">Description:</label></dt>
            <dd><input type="text" id="description" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><label for="calories">Calories:</label></dt>
            <dd><input type="number" id="calories" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>

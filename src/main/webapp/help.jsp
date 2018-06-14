<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>SQLCmd</title>
    </head>
    <body>
    Существующие команды:<br>
   <p>
       <a href="connect.jsp">connect</a><br>
            для подключения к базе данных, с которой будем работать нужно ввести имя базы, логин и пароль.<br>
       <a href="list.jsp">list</a><br>
            для получения списка всех таблиц базы, к которой подключились<br>
       <a href="cleanTable.jsp">cleanTable</a><br>
            для очистки всей таблицы от данных<br>
       <a href="createData.jsp">createData</a><br>
        create|tableName|column1|value1|column2|value2|...|columnN|valueN<br>
            для создания записи в таблице<br>
       <%--find|tableName<br>--%>
       <a href="find.jsp">typeTable</a><br>
            для получения содержимого таблицы tableName<br>
        <%--help<br>--%>
       <a href="help.jsp">help</a><br>
            для вывода этого списка на экран<br>
       <%-- exit>
       <a href="exit.jsp">exit</a><br>
            для выхода из программы<br>
            --%>
    <p>
        You can goto <a href="menu">Menu</a><br>
    </body>
</html>
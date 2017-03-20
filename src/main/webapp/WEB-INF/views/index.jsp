<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page contentType="text/html; charset=windows-1251" %>
<%@ page import="java.util.*, java.text.*" %>

<html>
<head>
    <title>Index страница JSP</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
</head>

<body>
Index страница JSP! Сегодня <%= getFormattedDate () %>
</body>
</html>

<%!
    String getFormattedDate ()
    {
        SimpleDateFormat sdf = new SimpleDateFormat ("dd.MM.yyyy hh:mm:ss");
        return sdf.format (new Date ());
    }
%>
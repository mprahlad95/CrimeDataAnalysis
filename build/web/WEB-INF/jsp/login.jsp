<%-- 
    Document   : login
    Created on : Apr 16, 2018, 11:08:50 PM
    Author     : Vigneet Sompura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello ${user.username}!</h1>
        (${user.longitude} - ${user.latitude})
        <a href="heatmap.htm">Heatmap</a>
    </body>
</html>

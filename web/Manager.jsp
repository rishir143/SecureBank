<%-- 
    Document   : Manager
    Created on : 5 Aug 2025, 4:45:33 pm
    Author     : local user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manager</title>
    </head>
    <body>
        <%
            String u_type, email;
            u_type = (String)session.getAttribute("u_type");
            email = (String)session.getAttribute("email");
            
            if (u_type == null || email == null) {
                response.sendRedirect("Login");
                return;
            }
            
            if(!(u_type.equals("manager"))) {
                response.sendRedirect("Login");
            }
        %>
        <jsp:include page="AllUsers.jsp"/>
    </body>
</html>

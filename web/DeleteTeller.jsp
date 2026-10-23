<%-- 
    Document   : DeleteTeller
    Created on : 20 Aug 2025, 5:24:55 pm
    Author     : local user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="BankServlet.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <%
            String u_type, email;
            u_type = (String) session.getAttribute("u_type");
            email = (String) session.getAttribute("email");

            if (u_type == null || email == null) {
                response.sendRedirect("Login");
                return;
            }

            if (!(u_type.equals("manager"))) {
                response.sendRedirect("Login");
                return;
            }
            
            String tellerEmail = request.getParameter("email");
            try {
                TellerDao.delete(tellerEmail);
            } catch(Exception ex) {
                System.out.println(ex);
            }
            
            response.sendRedirect("Accounts.jsp");
        %>
    </body>
</html>

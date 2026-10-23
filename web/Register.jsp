<%-- 
    Document   : Register
    Created on : 28 Jul 2025, 5:50:13 pm
    Author     : local user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="Bootstrap.html"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
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
            }
        %>
        <jsp:include page="nav.jsp"/>
        <div class="main">
            <form method="post" action="Register">
                <input type="text" name="name" class="form-control" placeholder="Full Name" required>
                <input type="text" name="account_no" class="form-control" placeholder="Account number" required>
                <input type="text" name="branch" class="form-control" placeholder="branch" required>
                <input type="number" min="500" name="balance" class="form-control" placeholder="Account Balance" required>
                <select name="account_type" class="form-select form-control" required>
                    <option value="saving">Saving Account</option>
                    <option value="current">current account</option>
                </select>
                <input type="number" min="6666666666" max="9999999999" name="contact" class="form-control" placeholder="contact" required>
                
                <input type="email" name="email" class="form-control" placeholder="email" required>
                <input type="password" name="pass" class="form-control" placeholder="enter password" required>
                <input type="password" name="confirm_pass" class="form-control" placeholder="confirm password" required>
                <input type="submit" value="Register Account" class="btn btn-primary form-control">
            </form>
            <a href="Manager.jsp">Back to home</a>
        </div>
    </body>
</html>

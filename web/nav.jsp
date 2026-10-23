<%-- 
    Document   : nav
    Created on : 1 Aug 2025, 5:37:23 pm
    Author     : local user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="Bootstrap.html"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <style>
            .nav{
                display: flex;
                justify-content: space-between;
                align-items: center;
                background-color: black;
                padding: 5px 10px;
            }
            .nav a {
                text-decoration: none;
                margin: 0 5px;
            }
            .left a {
                color: #ffe;
            }
        </style>
    </head>
    <body>
        <%
            String u_type, email;
            u_type = (String) session.getAttribute("u_type");
            email = (String) session.getAttribute("email");
            Long uBal = BankServlet.UserDao.getBalance(email);

            if (email == null || u_type == null) {
                response.sendRedirect("Login");
                return;
            }
        %>

        <div class="nav">
            <div class="left ps-2">
                <b><span class="text-white">My Bank</span></b>
                <%
                    if (u_type.equals("user")) {

                        out.print("<a href='Index'>Home</a>");
                        out.print("<a href='#'>Accounts</a>");
                        out.print("<a href='Statement.jsp'>Account Statements</a>");
                        out.print("<a href='FundTransfer.jsp'>Fund Transfer</a>");

                    } else if (u_type.equals("manager")) {

                        out.print("<a href='Manager.jsp'>Home</a>");
                        out.print("<a href='Accounts.jsp'>Accounts</a>");
                        out.print("<a href='Register.jsp'>Add new Account</a>");
                        out.print("<a href='#'>Feedback</a>");

                    } else if (u_type.equals("cashier")) {
                        out.print("<a href='Cashier.jsp'>Home</a>");
                    }
                %>

            </div>
            <div class="right">
                <%
                    if (u_type.equals("user")) {
                        
                        out.print("<a href='#' class='btn btn-outline-primary'>Account Balance : Rs " + uBal + "</a>");
                        out.print("<a href='Statement.jsp' class='btn btn-outline-primary'><i class='fa-solid fa-book'></i></a>");
                        out.print("<a href='Notification.jsp' class='btn btn-outline-primary'><i class='fa-solid fa-envelope'></i></a>");
                        out.print("<a href='#' class='btn btn-outline-primary'><i class='fa-solid fa-question'></i></a>");

                    } else if (u_type.equals("manager")) {
                        out.print("<a href='#' class='btn btn-outline-primary'>Welcome, Manager</a>");
                    } else if (u_type.equals("cashier")) {
                        out.print("<a href='#' class='btn btn-outline-primary'>Welcome, Cashier</a>");
                    }

                    out.print("<a href='Logout' class='btn btn-outline-danger'><i class='fa-solid fa-arrow-right-from-bracket'></i></a>");
                %>

            </div>
        </div>
    </body>
</html>

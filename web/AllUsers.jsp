<%-- 
    Document   : Accounts
    Created on : 7 Aug 2025, 4:23:12 pm
    Author     : local user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accounts</title>
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

        <div class="row mt-5">
            <div class="col-10 offset-1">
                <h2 class="text-center">All Accounts</h2>
                <%
                    ResultSet accounts = BankServlet.UserDao.AccountsData();
                    int index = 1;
                    if (!(accounts == null)) {
                        out.print("<table class='table table-bordered table-hover'>");
                        
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Holder Name</th>");
                        out.print("<th>Account No.</th>");
                        out.print("<th>Branch Name</th>");
                        out.print("<th>Current Balance</th>");
                        out.print("<th>Account Type</th>");
                        out.print("<th>contact</th>");
                        out.print("<th></th>");
                        out.print("</tr>");
                        
                        while (accounts.next()) {
                            
                            long account_no = accounts.getLong(2);
                        
                            out.print("<tr>");
                            out.print("<td>" + index + "</td>");
                            out.print("<td>" + accounts.getString(1) + "</td>");
                            out.print("<td>" + account_no + "</td>");
                            out.print("<td>" + accounts.getString(3) + "</td>");
                            out.print("<td>" + accounts.getLong(4) + "</td>");
                            out.print("<td>" + accounts.getString(5) + "</td>");
                            out.print("<td>" + accounts.getLong(6) + "</td>");
                            out.print("<td><a href='View.jsp?account_no="+ account_no +"' class='btn btn-success'>view</a> <a href='Notice?account_no="+account_no+"' class='btn btn-primary'>Send Notice</a><a href='Delete?account_no="+ account_no +"' class='btn btn-danger'>Delete</a></td>");
                            out.print("</tr>");

                            index++;
                        }
                        out.print("</table>");
                    }
                %>
            </div>
        </div>
    </body>
</html>

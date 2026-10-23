<%-- 
    Document   : Login
    Created on : 25 Jul 2025, 4:56:12 pm
    Author     : local user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="Bootstrap.html"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>

        </style>
    </head>
    <body>
        <div class="container">
            <div class="accordion" id="accordionExample">
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            User Login
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form method="post" action="Login">
                                <input type="hidden" name="u_type" value="user">
                                <input type="email" name="email" class="form-control" required>
                                <input type="pass" name="pass" class="form-control" required>
                                <input type="submit" value="Login" class="form-control btn btn-primary" required>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            Manager Login
                        </button>
                    </h2>
                    <div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form method="post" action="Login">
                                <input type="hidden" name="u_type" value="manager">
                                <input type="email" name="email" class="form-control" required>
                                <input type="pass" name="pass" class="form-control" required>
                                <input type="submit" value="Login" class="form-control btn btn-primary" required>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="accordion-item">
                    <h2 class="accordion-header">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            Cashier Login
                        </button>
                    </h2>
                    <div id="collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <form action="Login" method="post">
                                <input type="hidden" name="u_type" value="cashier">
                                <input type="email" name="email" class="form-control" required>
                                <input type="pass" name="pass" class="form-control" required>
                                <input type="submit" value="Login" class="form-control btn btn-primary" required>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankServlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Rishi 
 */
public class Login extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/Login.jsp");
        rd.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String u_type, email, pass;
        HttpSession Session = req.getSession();
        PrintWriter out = res.getWriter();

        u_type = req.getParameter("u_type");
        email = req.getParameter("email");
        pass = req.getParameter("pass");

        User u = new User();
        u.setU_type(u_type);
        u.setEmail(email);
        u.setPass(pass);

        if (u_type.equals("user")) {

            int validation = UserDao.read(u);

            switch (validation) {
                case 0 -> {
                    out.print("<h1 class='text-warning'>You are not not registered users</h1>");
                    RequestDispatcher rd = req.getRequestDispatcher("/Login.jsp");
                    rd.include(req, res);
                }
                case 1 -> {
                    Session.setAttribute("u_type", u_type);
                    Session.setAttribute("email", email);
                    res.sendRedirect("Index");
                }

                case 3 -> {
                    out.print("<h1 class='text-info'>Server Error occured</h1>");
                    RequestDispatcher rd = req.getRequestDispatcher("/Login.jsp");
                    rd.include(req, res);
                }
                default -> {
                    out.print("<h1 class='text-danger'>Wrong password</h1>");
                    RequestDispatcher rd = req.getRequestDispatcher("/Login.jsp");
                    rd.include(req, res);
                }
            }
        } else if (u_type.equals("manager")) {
            if (email.equals("manager@manager.com") && pass.equals("manager")) {
                Session.setAttribute("u_type", "manager");
                Session.setAttribute("email", email);
                res.sendRedirect("Manager.jsp");
            } else {
                out.print("<h1 class='text-danger'>Wrong email or password</h1>");
                RequestDispatcher rd = req.getRequestDispatcher("/Login.jsp");
                rd.include(req, res);
            }
        } else if (u_type.equals("cashier")) {
            int validation = TellerDao.login(u);

            if (validation == 1) {
                Session.setAttribute("u_type", u_type);
                Session.setAttribute("email", email);
                res.sendRedirect("Cashier.jsp");
            } else {
                out.print("<h1 class='text-danger'>Wrong email or password</h1>");
                RequestDispatcher rd = req.getRequestDispatcher("/Login.jsp");
                rd.include(req, res);
            }
        }
    }
}

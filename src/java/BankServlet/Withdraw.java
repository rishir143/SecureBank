/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 *
 * @author Rishi 
 */
public class Withdraw extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.sendRedirect("Cashier.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        long checkNo = 0, ammount = 0, account_no = 0;
        checkNo = Long.parseLong(req.getParameter("checkNo"));
        ammount = Long.parseLong(req.getParameter("ammount"));
        account_no = Long.parseLong(req.getParameter("account_no"));

        if (checkNo == 0 || ammount == 0) {
            out.print("<h2 class='text-danger'>All Fields required</h2>");
            RequestDispatcher rd = req.getRequestDispatcher("/Cashier.jsp");
            rd.include(req, res);
            return;
        }

        int status = TellerDao.withdraw(checkNo, ammount, account_no);

        if (status == 3) {
            out.print("<h2 class='text-success'>Check no already used</h2>");
            RequestDispatcher rd = req.getRequestDispatcher("/Cashier.jsp");
            rd.include(req, res);
            return;
        } else if (status == 4) {
            out.print("<h2 class='text-success'>insufficient balance</h2>");
            RequestDispatcher rd = req.getRequestDispatcher("/Cashier.jsp");
            rd.include(req, res);
            return;
        } else if (status == 2) {
            out.print("<h2 class='text-success'>Successfully withdrawn</h2>");
            RequestDispatcher rd = req.getRequestDispatcher("/Cashier.jsp");
            rd.include(req, res);
            return;
        } else {
            out.print("<h2 class='text-warning'>Failed</h2>");
            RequestDispatcher rd = req.getRequestDispatcher("/Cashier.jsp");
            rd.include(req, res);
            return;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Rishi 
 */
public class Notice extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RequestDispatcher rd = req.getRequestDispatcher("/Notice.jsp");
        rd.include(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String notice = req.getParameter("notice");
        long account_no = Long.parseLong(req.getParameter("account_no"));

        int status = Dao.sendNotice(account_no, notice);

        if (status > 0) {
            out.print("<h1 class='text-success'>Notice Send</h1>");
            RequestDispatcher rd = req.getRequestDispatcher("/Manager.jsp");
            rd.include(req, res);
        } else {
            out.print("<h1 class='text-danger'>Failed to insert data</h1>");
            RequestDispatcher rd = req.getRequestDispatcher("/Notice.jsp");
            rd.include(req, res);
        }
    }

}

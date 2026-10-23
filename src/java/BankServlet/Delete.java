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
public class Delete extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        
        long account_no = 0;
        account_no = Long.parseLong(req.getParameter("account_no"));
        
        int status = UserDao.DeleteUser(account_no);
        
        if (status == 2) {
            out.print("<h1 class='text-info'>successfully deleted</h1>");
            RequestDispatcher rd = req.getRequestDispatcher("/Accounts.jsp");
            rd.include(req, res);
        } else {
            out.print("<h1 class='text-danger'>failed to delete</h1>");
            RequestDispatcher rd = req.getRequestDispatcher("/Accounts.jsp");
            rd.include(req, res);
        }
    }
}

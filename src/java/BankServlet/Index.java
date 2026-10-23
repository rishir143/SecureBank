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
public class Index extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        if(!(session.getAttribute("u_type").equals("user"))) {
            res.sendRedirect("Login");
        }
        
        BankServlet.User u = BankServlet.UserDao.Account_Details((String)session.getAttribute("email"));
        session.setAttribute("u", u);
        RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
        rd.include(req, res);
    }
}

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
public class Logout extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("u_type");
        session.removeAttribute("email");
        res.sendRedirect("Login");
    }
}

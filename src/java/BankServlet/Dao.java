/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankServlet;

import java.sql.*;

/**
 *
 * @author Rishi 
 */
public class Dao {

    public static ResultSet getDetails(long account_no) {
        ResultSet rs = null;

        try {
            Connection con = UserDao.getConnection();

            PreparedStatement ps = con.prepareStatement("select * from accounts where account_no=?");
            ps.setLong(1, account_no);

            rs = ps.executeQuery();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return rs;
    }

    public static int sendNotice(long account_no, String notice) {
        int status = 0;

        if (notice.equals("")) {
            return status;
        }

        try {
            Connection con = UserDao.getConnection();
            Statement stmt = con.createStatement();
            String q = "create table if not exists notice (account_no bigint not null, notice varchar(50) not null, foreign key (account_no) references users(account_no))";
            stmt.executeUpdate(q);

            PreparedStatement ps = con.prepareStatement("insert into notice(account_no, notice) values(?, ?)");
            ps.setLong(1, account_no);
            ps.setString(2, notice);

            status = ps.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return status;
    }
    
    public static ResultSet getNotification(String email) {
        ResultSet rs = null;
        
        try {
            Connection con = UserDao.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM notice WHERE account_no = (SELECT account_no FROM users WHERE email=?)");
            ps.setString(1, email);
            
            rs = ps.executeQuery();
        }catch(Exception ex) {
            System.out.println(ex);
        }
        return rs;
    }
}

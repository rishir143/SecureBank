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
public class TellerDao {

    // Creating Cashier Account
    public static int createTeller(String email, String pass) {
        int status = 0;

        try {
            Connection con = UserDao.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("create table if not exists teller(email varchar(50) primary key, pass varchar(50) not null, account_type varchar(50))");

            PreparedStatement ps = con.prepareStatement("insert into teller(email, pass, account_type) values(?, ?, ?)");
            ps.setString(1, email);
            ps.setString(2, pass);
            ps.setString(3, "Cashier");

            status = ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return status;
    }

    // function to read all teller accounts
    public static ResultSet read() {
        ResultSet rs = null;
        try {
            Connection con = UserDao.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM teller");
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return rs;
    }

    public static void delete(String email) {
        try {
            Connection con = UserDao.getConnection();
            PreparedStatement ps = con.prepareStatement("delete from teller where email=?");
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    // login dao for cashier
    public static int login(User u) {
        int validation = 0;

        try {
            Connection con = UserDao.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM teller WHERE email=?");
            ps.setString(1, u.getEmail());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (u.getPass().equals(rs.getString(2))) {
                    validation = 1;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return validation;
    }

    // Get Account Details
    public static ResultSet getDetails(long account_no) {
        ResultSet rs = null;
        try {
            Connection con = UserDao.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts where account_no=?");
            ps.setLong(1, account_no);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return rs;
    }

    // withdraw
    public static int withdraw(long checkNo, long ammount, long account_no) {
        int status = 0;

        try {
            Connection con = UserDao.getConnection();

            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS transactions("
                    + "account_no bigint not null,"
                    + " checkNo bigint primary key,"
                    + " ammount bigint not null,"
                    + " transaction_type varchar(50) not null,"
                    + " date date default(current_date),"
                    + " foreign key (account_no) references accounts(account_no))");
            ps.execute();

            // validations
            PreparedStatement psmt = con.prepareStatement("SELECT * FROM transactions WHERE checkNo=?");
            psmt.setLong(1, checkNo);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return 3;
            }

            PreparedStatement psmt1 = con.prepareStatement("SELECT * FROM accounts WHERE account_no=?");
            psmt1.setLong(1, account_no);
            ResultSet rs1 = psmt1.executeQuery();
            if (rs1.next()) {
                long balance = rs1.getLong(4);
                if ((balance - ammount) < 0) {
                    return 4;
                }
            }
            
            // actual work
            PreparedStatement ps1 = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_no=?");
            ps1.setLong(1, ammount);
            ps1.setLong(2, account_no);

            int i = ps1.executeUpdate();
            if (i > 0) {
                status = 1;
            }

            PreparedStatement ps2 = con.prepareStatement("INSERT INTO transactions (account_no, checkNo, ammount, transaction_type) VALUES(?, ?, ?, ?)");
            ps2.setLong(1, account_no);
            ps2.setLong(2, checkNo);
            ps2.setLong(3, ammount);
            ps2.setString(4, "withdraw");

            int j = ps2.executeUpdate();
            if (j > 0) {
                status = 2;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return status;
    }

    // Deposite
    public static int deposite(long checkNo, long ammount, long account_no) {
        int status = 0;

        try {
            Connection con = UserDao.getConnection();

            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS transactions("
                    + "account_no bigint not null,"
                    + "checkNo bigint primary key,"
                    + " ammount bigint not null,"
                    + " transaction_type varchar(50) not null,"
                    + " date date default(current_date),"
                    + " foreign key (account_no) references accounts(account_no))");
            ps.execute();

            PreparedStatement psmt = con.prepareStatement("SELECT * FROM transactions WHERE checkNo=?");
            psmt.setLong(1, checkNo);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return 3;
            }

            PreparedStatement ps1 = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_no=?");
            ps1.setLong(1, ammount);
            ps1.setLong(2, account_no);

            int i = ps1.executeUpdate();
            if (i > 0) {
                status = 1;
            }

            PreparedStatement ps2 = con.prepareStatement("INSERT INTO transactions (account_no, checkNo, ammount, transaction_type) VALUES(?, ?, ?, ?)");
            ps2.setLong(1, account_no);
            ps2.setLong(2, checkNo);
            ps2.setLong(3, ammount);
            ps2.setString(4, "deposite");

            int j = ps2.executeUpdate();
            if (j > 0) {
                status = 2;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return status;
    }
}

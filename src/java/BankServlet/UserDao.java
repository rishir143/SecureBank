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
public class UserDao {

    private static final String DEFAULT_DB_URL = "jdbc:mysql://localhost:3306/bank?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC";
    private static volatile boolean schemaInitialized;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    environment("DB_URL", DEFAULT_DB_URL),
                    environment("DB_USER", "root"),
                    environment("DB_PASSWORD", "")
            );
            initializeSchema(con);
            return con;
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to connect to the SecureBank database", ex);
        }
    }

    private static String environment(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }

    private static synchronized void initializeSchema(Connection con) throws SQLException {
        if (schemaInitialized) {
            return;
        }

        try (Statement statement = con.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "account_no BIGINT PRIMARY KEY, email VARCHAR(255) NOT NULL UNIQUE, pass VARCHAR(255) NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS accounts ("
                    + "name VARCHAR(255) NOT NULL, account_no BIGINT PRIMARY KEY, branch VARCHAR(255) NOT NULL, "
                    + "balance BIGINT NOT NULL, account_type VARCHAR(50) NOT NULL, contact BIGINT NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS teller ("
                    + "email VARCHAR(255) PRIMARY KEY, pass VARCHAR(255) NOT NULL, account_type VARCHAR(50) NOT NULL)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS transactions ("
                    + "account_no BIGINT NOT NULL, checkNo BIGINT PRIMARY KEY, ammount BIGINT NOT NULL, "
                    + "transaction_type VARCHAR(50) NOT NULL, date DATE DEFAULT (CURRENT_DATE), "
                    + "FOREIGN KEY (account_no) REFERENCES accounts(account_no))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS transfer ("
                    + "account_no BIGINT NOT NULL, receiver BIGINT NOT NULL, ammount BIGINT NOT NULL, "
                    + "transaction_type VARCHAR(50) NOT NULL, date DATE DEFAULT (CURRENT_DATE), "
                    + "FOREIGN KEY (account_no) REFERENCES accounts(account_no))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS notice ("
                    + "account_no BIGINT NOT NULL, notice VARCHAR(255) NOT NULL, "
                    + "FOREIGN KEY (account_no) REFERENCES users(account_no))");
        }
        schemaInitialized = true;
    }

    public static int save(User u) {
        int status = 0;

        try {
            Connection con = UserDao.getConnection();

            PreparedStatement ps1 = con.prepareStatement("INSERT INTO users (account_no, email, pass) values(?, ?, ?)");
            ps1.setLong(1, u.getAccount_no());
            ps1.setString(2, u.getEmail());
            ps1.setString(3, u.getPass());

            status += ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("INSERT INTO accounts (name, account_no, branch, balance, account_type, contact) values (?, ?, ?, ?, ?, ?)");
            ps2.setString(1, u.getName());
            ps2.setLong(2, u.getAccount_no());
            ps2.setString(3, u.getBranch());
            ps2.setLong(4, u.getBalance());
            ps2.setString(5, u.getAccount_type());
            ps2.setLong(6, u.getContact());

            status += ps2.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return status;
    }

    public static int read(User u) {
        int validation = 0;

        try {
            Connection con = UserDao.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from users where email=?");
            ps.setString(1, u.getEmail());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getString(3).equals(u.getPass())) {
                    validation = 1;
                } else {
                    validation = 2;
                }
            }

        } catch (Exception ex) {
            validation = 3;
            System.out.println(ex);
        }

        return validation;
    }

    public static User Account_Details(String email) {
        User u = new User();

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("select * from accounts where account_no=(select account_no from users where email=?)");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u.setName(rs.getString(1));
                u.setAccount_no(rs.getLong(2));
                u.setBranch(rs.getString(3));
                u.setBalance(rs.getLong(4));
                u.setAccount_type(rs.getString(5));
                u.setContact(rs.getLong(6));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return u;
    }

    public static ResultSet AccountsData() {
        ResultSet rs = null;

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("select * from accounts");

            rs = ps.executeQuery();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return rs;
    }

    public static int DeleteUser(long account_no) {
        int status = 0;

        try {
            Connection con = UserDao.getConnection();

            PreparedStatement ps1 = con.prepareStatement("delete from accounts where account_no=?");
            ps1.setLong(1, account_no);

            status += ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("delete from users where account_no=?");
            ps2.setLong(1, account_no);

            status += ps2.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return status;
    }

    // Statement Page
    public static ResultSet statement(String email) {
        ResultSet rs = null;

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  * FROM transactions "
                    + "WHERE account_no = (SELECT account_no FROM users WHERE email=?) "
                    + "UNION ALL "
                    + "SELECT  * FROM transfer "
                    + "WHERE account_no = (SELECT account_no FROM users WHERE email=?)"
            );
            ps.setString(1, email);
            ps.setString(2, email);

            rs = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return rs;
    }

    // Tranfer money from one user to another
    public static int FundTransfer(String email, long account_no, long ammount) {
        int status = 0;

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT account_no FROM users WHERE email=?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long user = rs.getLong(1);

                if (user == account_no) {
                    return 2;
                }

                PreparedStatement ps1 = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_no=? AND (balance - ?) >= 0");
                PreparedStatement ps2 = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_no=?");
                ps1.setLong(1, ammount);
                ps1.setLong(2, user);
                ps1.setLong(3, ammount);
                ps2.setLong(1, ammount);
                ps2.setLong(2, account_no);

                int i = ps1.executeUpdate();

                if (i > 0) {
                    int j = ps2.executeUpdate();
                    if (j > 0) {

                        PreparedStatement ps4 = con.prepareStatement("CREATE TABLE IF NOT EXISTS transfer("
                                + "account_no bigint not null,"
                                + " receiver bigint,"
                                + " ammount bigint not null,"
                                + " transaction_type varchar(50) not null,"
                                + " date date default(current_date),"
                                + " foreign key (account_no) references accounts(account_no))");
                        ps4.execute();

                        PreparedStatement ps3 = con.prepareStatement("INSERT INTO transfer (account_no, receiver, ammount, transaction_type) VALUES(?, ?, ?, ?)");
                        ps3.setLong(1, user);
                        ps3.setLong(2, account_no);
                        ps3.setLong(3, ammount);
                        ps3.setString(4, "Transfer");
                        ps3.executeUpdate();
                        return 4;
                    }
                } else {
                    status = 3;
                }
            } else {
                status = 1;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return status;
    }

    // get balance function
    public static Long getBalance(String email) {
        Long balance = null;

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT balance FROM accounts WHERE account_no = (SELECT account_no FROM users WHERE email=?)");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                balance = rs.getLong(1);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return balance;
    }
}

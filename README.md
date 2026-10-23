# SecureBank
This is a Bank Management System built with Java Servlets and JSP (JavaServer Pages), designed to manage banking operations for different user roles. The system uses a MySQL database for data persistence and is intended to be run on a web server like Apache Tomcat.

## Author
Rishi

## Features
The application provides functionalities for three types of users:
- **User:** A regular customer with a bank account.
- **Manager:** An administrator who manages user and cashier accounts.
- **Cashier:** A teller who handles deposits and withdrawals.

### User Features
- **Account Dashboard**: Users can view their account balance, recent notices from the bank, and links to other banking services.
- **Account Statements**: View a detailed history of all transactions, including withdrawals, deposits, and fund transfers.
- **Fund Transfer**: Transfer money to another bank account by entering the recipient's account number and the amount to be transferred.
- **Notifications**: Check notices and updates sent by the bank manager.

### Manager Features
- **Dashboard**: The main page for managers lists all user accounts.
- **User Account Management**: View, send notices to, or delete any user account.
- **Teller Account Management**: Add or delete cashier accounts.
- **Customer Registration**: Register new user accounts with a minimum starting balance of Rs. 500.

### Cashier Features
- **Account Information**: Retrieve a user's account details by entering their account number.
- **Deposit**: Accept deposits from users by entering a check number and the amount. The system prevents the use of duplicate check numbers.
- **Withdrawal**: Process cash withdrawals. The system validates the account balance to prevent withdrawals if there's insufficient funds and also checks for duplicate check numbers.

## Technologies Used
- **Backend**: Java, Servlets, JSP
- **Frontend**: HTML, CSS, Bootstrap, Font Awesome
- **Database**: MySQL
- **IDE**: Apache NetBeans

## Project Structure
- `src/java/BankServlet/`
  - `Dao.java`: Provides data access methods for fetching user details and notices.
  - `Deposite.java`: Servlet to handle deposit transactions.
  - `Login.java`: Manages user authentication for all three user roles.
  - `Notice.java`: Servlet for sending notices from a manager to a specific user.
  - `Register.java`: Handles the registration of new user accounts.
  - `TellerDao.java`: Manages cashier accounts and handles deposit and withdrawal logic.
  - `Transfer.java`: Processes fund transfers between user accounts.
  - `User.java`: A simple Java Bean (POJO) to represent user data.
  - `UserDao.java`: Manages database operations for user accounts, including saving, reading, updating, and deleting data.
  - `Withdraw.java`: Servlet to handle withdrawal transactions.
- `web/`
  - `AllUsers.jsp`: Displays a list of all user accounts for the manager.
  - `Accounts.jsp`: Displays and manages all staff (cashier) accounts.
  - `Cashier.jsp`: The main interface for cashiers to perform transactions.
  - `FundTransfer.jsp`: Interface for users to initiate a fund transfer.
  - `index.jsp`: The main dashboard for a logged-in user.
  - `Login.jsp`: The login page for all user types.
  - `Manager.jsp`: The main page for managers.
  - `nav.jsp`: A reusable navigation bar that changes based on the user's role.
  - `Register.jsp`: The form for managers to create new user accounts.
  - `Statement.jsp`: Displays the user's transaction history.
  - `View.jsp`: Shows detailed information for a specific user account.
  - `web.xml`: The deployment descriptor that maps servlets to URL patterns.

## How to Run
The recommended route runs MySQL, Tomcat, and the required Java dependencies together:

```bash
docker compose up --build
```

Open http://localhost:8080/Login. The application creates its database tables at startup. Stop it with `docker compose down`; add `-v` to also remove the local database volume.

For an existing Tomcat 9 and MySQL installation, build the deployable WAR with Java 17+ and Maven:

```bash
mvn package
```

Deploy `target/SecureBank.war` to Tomcat 9. Configure the database with `DB_URL`, `DB_USER`, and `DB_PASSWORD`. If omitted, the app uses `jdbc:mysql://localhost:3306/bank`, user `root`, and a blank password for local development.

> The bundled manager login is a demo credential (`manager@manager.com` / `manager`). Change it before exposing the application outside local development.

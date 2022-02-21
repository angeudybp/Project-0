package com.revature;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    static User currentUser;

    public static void main(String[] args) throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/revature/dbConfig");
        Scanner scanner = new Scanner(System.in);
        int choice;
        String username, password;
        System.out.println("*------------Welcome to the BankApp------------*");
        System.out.println("Do you have an account? (1-yes 2-no)");
        choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("please log in:\nusername:");
            username = scanner.next();
            System.out.println("password:");
            password = scanner.next();
            loggIn(username, password);
        } else if (choice == 2) {
            System.out.println("Please create a new account!\nusername:");
            username = scanner.next();
            System.out.println("password:");
            password = scanner.next();
            createNewUser(username, password);
        }
        if (currentUser!=null) {
            System.out.println("Hi " + currentUser.getUsername() + " are you a customer or and employee?(1-customer 2-employee)");
            choice = scanner.nextInt();
            if (choice == 1) {
                optionsCustomer(currentUser);
            } else if (choice == 2) {
                System.out.println("please provide the admin password:");
                password = scanner.next();
                String admin = resourceBundle.getString("adminPassword");
                if (password.equals(admin)) {
                    optionsEmployee(currentUser);
                } else {
                    System.out.println("your user does not have privileges to access these options!");
                }
            }
        }

    }

    public static void optionsCustomer(User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        if (user.isLogged()) {
            System.out.println("Thanks for logging in!\n what do you wish to do right now?");
            System.out.println("""
                    1- Apply for new account
                    2-view balance
                    3-make a withdraw
                    4-make a deposit
                    5-make a transfer
                    6-exit""");
            choice = scanner.nextInt();
            while (choice != 6) {
                switch (choice) {
                    case 1 -> applyForBankAcc();
                    case 2 -> System.out.println("Your account balance is: "+accountBalance());
                    case 3 -> makeWithdraw();
                    case 4 -> makeDeposit();
                    case 5 -> makeTransfer();
                }
                System.out.println("""
                        1- Apply for new account
                        2-view balance
                        3-make a withdraw
                        4-make a deposit
                        5-make a transfer
                        6-exit""");
                choice = scanner.nextInt();
            }
            System.out.println("Thanks for using the BankApp, Goodbye :)!");
            loggOff(user);
        } else {
            System.out.println("You need to be logged in in order to access these options!");
        }
    }

    public static void optionsEmployee(User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        if (user.isLogged()) {
            System.out.println("Thanks for logging in, our systems detect that your account corresponds to an employee" +
                    ".\n what do you wish to do?");
            System.out.println("""
                    1- Approve or reject accounts
                    2- view customer accounts
                    3-create new Employee account
                    4-exit""");
            choice = scanner.nextInt();
            while (choice != 4) {
                switch (choice) {
                    case 1 -> accountSupervision();
                    case 2 -> customerLookUp();
                    case 3-> createNewEmployee();
                }
                System.out.println("""
                        Do you wish to perform another action?
                        1- Approve or reject accounts
                        2- view customer accounts
                        3-create new Employee account
                        4-exit""");
                choice = scanner.nextInt();
            }
            System.out.println("Thanks for using The BankApp! Goodbye :)!");
            loggOff(user);

        } else {
            System.out.println("You need to be logged in in order to access these options!");
        }

    }


    public static void loggIn(String username, String password) throws SQLException {

        UserDao userDao = new UserDaoImp();


        for (User e : userDao.getUsers()) {
            if (username.toLowerCase().equals(e.getUsername()) && password.equals(e.getPassword())) {
                userDao.updateLoginUser(e, 2);
                currentUser = e;
            }
        }
        if (currentUser==null){
            System.out.println("There is no existing records with those credentials. Please create an account!");
        }



    }

    public static void loggOff(User user) throws SQLException {
        UserDao userDao = new UserDaoImp();
        userDao.updateLoginUser(user, 1);
        currentUser = null;

    }

    public static void createNewUser(String username, String password) throws SQLException {
        UserDao userDao = new UserDaoImp();
        User user = new User(username.toLowerCase(), password);
        user.setLogged(true);
        userDao.addUser(user);

        currentUser = user;
    }

    public static void applyForBankAcc() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        CustomerDao customerDao = new CustomerDaoImp();
        String name, last, email;
        double balance;
        System.out.println("What is your name?");
        name = scanner.next();
        System.out.println("What is your last name?");
        last = scanner.next();
        System.out.println("What is your email?");
        email = scanner.next();
        System.out.println("What will be your initial deposit?");
        balance = scanner.nextDouble();

        Customer customer = new Customer(name, last, email, balance);
        customerDao.addCustomer(customer);
    }

    public static double accountBalance() throws SQLException {
        int choice;
        CustomerDao customerDao = new CustomerDaoImp();
        int id;
        String name;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you know your account id?(1-yes 2-no)");
        choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("please enter your customer id:");
            id = scanner.nextInt();
            System.out.println("Thanks! "+customerDao.getCustomerById(id).getName()+" "+customerDao.getCustomerById(id).getLast());
            return customerDao.getCustomerById(id).getBalance();

        } else {
            System.out.println("Since you don't have your customer id, lest try with your name:");
            name = scanner.next();
            for (Customer c : customerDao.getCustomers()
            ) {
                if (c.getName().equals(name)) {
                    return c.getBalance();
                }

            }
            return -1;

        }
    }

    public static void makeDeposit() throws SQLException {
        CustomerDao customerDao = new CustomerDaoImp();
        Scanner scanner = new Scanner(System.in);
        double amount;
        int id;
        String name;
        int choice;
        System.out.println("do you know your account id? (1-yes 2-no)");
        choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("What is your account id?");
            id = scanner.nextInt();
            System.out.println("What amount do you wish to deposit?");
            amount = scanner.nextDouble();
            customerDao.getCustomerById(id).deposit(amount);
        } else if (choice == 2) {
            System.out.println("Since you don't know your account id, let's try with your name:");
            name = scanner.next();
            for (Customer c : customerDao.getCustomers()
            ) {
                if (c.getName().equals(name)) {
                    System.out.println("What amount do you wish to deposit?");
                    amount = scanner.nextDouble();
                    c.deposit(amount);
                }

            }
        }

    }

    public static void makeWithdraw() throws SQLException {
        CustomerDao customerDao = new CustomerDaoImp();
        Scanner scanner = new Scanner(System.in);
        double amount;
        int id;
        String name;
        int choice;
        System.out.println("do you know your account id? (1-yes 2-no)");
        choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("What is your account id?");
            id = scanner.nextInt();
            System.out.println("What amount do you wish to withdraw?");
            amount = scanner.nextDouble();
            customerDao.getCustomerById(id).withdraw(amount);
        } else if (choice == 2) {
            System.out.println("Since you don't know your account id, let's try with your name:");
            name = scanner.next();
            for (Customer c : customerDao.getCustomers()
            ) {
                if (c.getName().equals(name)) {
                    System.out.println("What amount do you wish to withdraw?");
                    amount = scanner.nextDouble();
                    c.withdraw(amount);
                }

            }
        }
    }

    public static void customerLookUp() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Employee employee;
        EmployeeDao employeeDao = new EmployeeDaoImp();
        int id;
        System.out.println("Please provide your employee id to continue:");
        id = scanner.nextInt();
        employee = employeeDao.getEmployeeById(id);
        System.out.println("Hi " + employee.getName() + " welcome to the customer look up system!");
        employee.getCustomerInfo();
    }

    public static void accountSupervision() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Employee employee;
        CustomerDao customerDao = new CustomerDaoImp();
        EmployeeDao employeeDao = new EmployeeDaoImp();
        int id, choice;
        System.out.println("Please provide you employee id to continue:");
        id = scanner.nextInt();
        employee = employeeDao.getEmployeeById(id);
        System.out.println("Hi " + employee.getName() + " " + employee.getLast() + " Here's the list of customers:");
        for (Customer c : customerDao.getCustomers()
        ) {
            System.out.println(c.toString());
        }
        System.out.println("Do you wish to make any changes for these accounts?(1-yes 2-no");
        choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("please provide the customer's id you want to remove:");
            id = scanner.nextInt();
            customerDao.deleteCustomer(id);
        } else {
            System.out.println("Accounts kept as they are!");
        }
    }

    public static void makeTransfer() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        CustomerDao customerDao = new CustomerDaoImp();
        Customer customer;
        int id, choice;
        double amount;
        String name;

        System.out.println("Please confirm your customer id to start this transfer:");
        id = scanner.nextInt();
        customer = customerDao.getCustomerById(id);

        System.out.println("Do you know the customer id of the account you wish to transfer to? (1-yes 2-no)");
        choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("please enter id:");
            id = scanner.nextInt();
            System.out.println("How much would you like to transfer?");
            amount = scanner.nextDouble();
            customer.transfer(customerDao.getCustomerById(id), amount);
        } else if (choice == 2) {
            System.out.println("Since you don't have the id let's try with the name:");
            name = scanner.next();
            System.out.println("how much would you like to deposit?");
            amount = scanner.nextDouble();
            for (Customer c : customerDao.getCustomers()
            ) {
                if (c.getName().equals(name)) {
                    customer.transfer(c, amount);
                }

            }
        } else {
            System.out.println("Wrong input provided!");
        }

    }
    public static void createNewEmployee() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        EmployeeDao employeeDao = new EmployeeDaoImp();
        String name, last, email;

        System.out.println("What is your name?");
        name = scanner.next();
        System.out.println("What is your last name?");
        last = scanner.next();
        System.out.println("What is your email?");
        email = scanner.next();

        Employee employee = new Employee(name, last, email);
        employeeDao.addEmployee(employee);
    }


}

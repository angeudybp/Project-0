package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Customer {
    private String name;
    private String last;
    private String email;
    private int id;
    private double balance;

    public Customer(String name, String last, String email, double balance){
        this.name = name;
        this.last = last;
        this.email = email;
        this.balance = balance;
    }


    public Customer(String name, String last, String email, int id, double balance) {
        this.name = name;
        this.last = last;
        this.email = email;
        this.id = id;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", last='" + last + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", balance=" + balance +
                '}';
    }

    public void deposit(double amount) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        if (amount>0){
            balance += amount;
            String query = "update customer set balance =? where cust_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1,balance);
            preparedStatement.setInt(2,id);
            int count = preparedStatement.executeUpdate();
            if (count>0){
                System.out.println("Deposit completed successfully");
            }
        }else {
            System.out.println("Error! cannot deposit a negative amount!");
        }


    }
    public void withdraw(double amount) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        if (amount>balance){
            System.out.println("Insufficient funds to make this transaction");
        }else if (amount<0){
            System.out.println("Cannot withdraw negative amount!");
        }else {
            balance-= amount;
            String query = "update customer set balance =? where cust_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1,balance);
            preparedStatement.setInt(2,id);
            int count = preparedStatement.executeUpdate();
            if (count>0){
                System.out.println("Withdrawal completed successfully!");

            }
        }

    }
    public void transfer(Customer c, double amount) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        if (amount<0){
            System.out.println("Cannot transfer a negative amount!");
        }else if (amount>balance){
            System.out.println("Insufficient funds to make transaction");
        }else {
            balance-=amount;
            c.deposit(amount);

            String query = "update customer set balance =? where cust_id =?";
            String query2 ="update customer set balance =? where cust_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
            preparedStatement.setDouble(1,balance);
            preparedStatement.setInt(2,id);
            preparedStatement1.setDouble(1,c.getBalance());
            preparedStatement1.setInt(2,c.getId());

            int count = preparedStatement.executeUpdate()+ preparedStatement1.executeUpdate();
            if (count>1){
                System.out.println("Transfer successful!");

            }

        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }


}

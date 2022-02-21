package com.revature;

import java.sql.*;
import java.util.Scanner;

public class Employee {
    private String name;
    private String last;
    private String email;
    private int id;

    public Employee(String name, String last, String email) {
        this.name = name;
        this.last = last;
        this.email = email;

    }
    public Employee(String name, String last, String email, int id) {
        this.name = name;
        this.last = last;
        this.email = email;
        this.id = id;
    }

    public void getCustomerInfo() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        CustomerDao customerDao = new CustomerDaoImp();
        String name;
        int id, choice;
        System.out.println("Do you have the customer's id? (1- yes 2-no)");
        choice = scanner.nextInt();
        if (choice==1){
            System.out.println("Please enter the customer's id:");
            id= scanner.nextInt();
            System.out.println(customerDao.getCustomerById(id).toString());
        }else if (choice==2){
            System.out.println("Since you don't have the customer's id let's try with the name:");
            name= scanner.next();
            for (Customer c: customerDao.getCustomers()
                 ) {
                if (c.getName().equals(name)){
                    System.out.println(c.toString());
                }

            }
        }else {
            System.out.println("Error, incorrect input provided!");
        }

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee() {

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


}

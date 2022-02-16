package com.revature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDaoImp implements CustomerDao {
    Connection connection;

    public CustomerDaoImp() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        String query = "insert into customer (name,last,email,balance) values (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getLast());
        preparedStatement.setString(3, customer.getEmail());
        preparedStatement.setDouble(4, customer.getBalance());
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Customer added successfully");
        } else {
            System.out.println("Something went wrong!");
        }

    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("which field do you wish to update?\n1-name\n2-last\n3-email");
        int choice = scanner.nextInt();
        String update;
        switch (choice) {
            case 1 -> {
                String query = "update customer set name=? where cust_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println("what do you wish to update this field to?");
                update = scanner.next();
                preparedStatement.setString(1, update);
                preparedStatement.setInt(2, customer.getId());
                int count = preparedStatement.executeUpdate();
                if (count > 0) {
                    System.out.println("records updated successfully");
                } else {
                    System.out.println("something went wrong!");
                }
            }
            case 2 -> {
                String query = "update customer set last=? where cust_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println("what do you wish to update this field to?");
                update = scanner.next();
                preparedStatement.setString(1, update);
                preparedStatement.setInt(2, customer.getId());
                int count = preparedStatement.executeUpdate();
                if (count > 0) {
                    System.out.println("records updated successfully");
                } else {
                    System.out.println("something went wrong!");
                }
            }
            case 3 -> {
                String query = "update customer set email=? where cust_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println("what do you wish to update this field to?");
                update = scanner.next();
                preparedStatement.setString(1, update);
                preparedStatement.setInt(2, customer.getId());
                int count = preparedStatement.executeUpdate();
                if (count > 0) {
                    System.out.println("records updated successfully");
                } else {
                    System.out.println("something went wrong!");
                }
            }
            default -> {
                System.out.println("you didn't choose a proper option");
            }
        }

    }

    @Override
    public void deleteCustomer(int id) throws SQLException {
        String query = "delete from customer where cust_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Customer deleted successfully!");
        } else {
            System.out.println("Something went wrong!");
        }

    }

    @Override
    public List<Customer> getCustomers() throws SQLException {
        String query = "select * from customer";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String last = resultSet.getString("last");
            String email = resultSet.getString("email");
            int id = resultSet.getInt("cust_id");
            double balance = resultSet.getDouble("balance");
            customers.add(new Customer(name, last, email, id, balance));
        }
        return customers;
    }

    @Override
    public Customer getCustomerById(int id) throws SQLException {
        for (Customer c : getCustomers()
        ) {
            if (c.getId() == id) {
                return c;
            }
        }
        System.out.println("customer is not in the records.");
        return null;
    }
}

package com.revature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeDaoImp implements EmployeeDao {
    Connection connection;

    public EmployeeDaoImp() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addEmployee(Employee employee) throws SQLException {
        String query = "insert into employee (name,last,email) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, employee.getName());
        preparedStatement.setString(2, employee.getLast());
        preparedStatement.setString(3, employee.getEmail());
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Employee saved");
        } else {
            System.out.println("something went wrong!");
        }

    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("which field do you wish to update?\n1-name\n2-last\n3-email");
        int choice = scanner.nextInt();
        String update;
        switch (choice) {
            case 1 -> {
                String query = "update employee set name=? where emp_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println("what do you wish to update this field to?");
                update = scanner.next();
                preparedStatement.setString(1, update);

                preparedStatement.setInt(2, employee.getId());
                int count = preparedStatement.executeUpdate();
                if (count > 0) {
                    System.out.println("records updated successfully");
                } else {
                    System.out.println("something went wrong!");
                }
            }
            case 2 -> {
                String query = "update employee set last=? where emp_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println("what do you wish to update this field to?");
                update = scanner.next();
                preparedStatement.setString(1, update);

                preparedStatement.setInt(2, employee.getId());

                int count = preparedStatement.executeUpdate();
                if (count > 0) {
                    System.out.println("records updated successfully");
                } else {
                    System.out.println("something went wrong!");
                }
            }
            case 3 -> {
                String query = "update employee set email=? where emp_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println("what do you wish to update this field to?");
                update = scanner.next();
                preparedStatement.setString(1, update);
                preparedStatement.setInt(2, employee.getId());
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
    public void deleteEmployee(int id) throws SQLException {
        String query = "delete from employee where emp_id ='?'";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Employee deleted successfully");
        } else {
            System.out.println("Something went wrong!");
        }

    }

    @Override
    public List<Employee> getEmployees() throws SQLException {
        String query = "Select * from employee";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Employee> employees = new ArrayList<Employee>();
        while (resultSet.next()) {
            int id = resultSet.getInt("emp_id");
            String name = resultSet.getString("name");
            String last = resultSet.getString("last");
            String email = resultSet.getString("email");
            employees.add(new Employee(name, last, email, id));
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(int id) throws SQLException {
        for (Employee e : getEmployees()
        ) {
            if (e.getId() == id) {
                return e;
            }

        }
        return null;
    }
}

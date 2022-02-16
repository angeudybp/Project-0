package com.revature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDaoImp implements UserDao{
    Connection connection;
    public UserDaoImp(){
        this.connection = ConnectionFactory.getConnection();
    }
    @Override
    public void addUser(User user) throws SQLException {
        String query = "insert into User (username,password,is_logged) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setBoolean(3, user.isLogged());

        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("User saved");
        } else {
            System.out.println("something went wrong!");
        }

    }

    @Override
    public void updateUser(User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("which field do you wish to update?\n1-username\n2-password");
        int choice = scanner.nextInt();
        String update;
        switch (choice) {
            case 1 -> {
                String query = "update user set username=? where user_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println("what do you wish to update this field to?");
                update = scanner.next();
                preparedStatement.setString(1, update);
                preparedStatement.setInt(2, user.getUserId());
                int count = preparedStatement.executeUpdate();
                if (count > 0) {
                    System.out.println("records updated successfully");
                } else {
                    System.out.println("something went wrong!");
                }
            }
            case 2 -> {
                String query = "update user set password=? where user_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                System.out.println("what do you wish to update this field to?");
                update = scanner.next();
                preparedStatement.setString(1, update);
                preparedStatement.setInt(2, user.getUserId());
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
    public void updateLoginUser(User user, int i) throws SQLException {
        if (i == 1) {
            user.setLogged(false);
            String q = "update user set is_logged=? where user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(q);
            preparedStatement.setBoolean(1, user.isLogged());
            preparedStatement.setInt(2, user.getUserId());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println(user.getUsername() + " logged off successfully!");
            } else {
                System.out.println("Something went wrong!");
            }
        } else if (i == 2) {
            user.setLogged(true);
            String q = "update user set is_logged=? where user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(q);
            preparedStatement.setBoolean(1, user.isLogged());
            preparedStatement.setInt(2, user.getUserId());
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println(user.getUsername() + " logged in successfully!");
            } else {
                System.out.println("Something went wrong!");
            }
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        String query = "delete from user where user_id ='?'";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("User deleted successfully");
        } else {
            System.out.println("Something went wrong!");
        }

    }

    @Override
    public void isLogged(User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean b;
        String query = "update user set is_logged=? where user_id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        System.out.println("Do you wish to log in(1-yes 2-no)?");
        choice = scanner.nextInt();
        b= choice == 1;
        preparedStatement.setBoolean(1,b);
        preparedStatement.setInt(2, user.getUserId());
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println(user.getUsername()+" logged in successfully");
        } else {
            System.out.println("something went wrong!");
        }
    }


    @Override
    public List<User> getUsers() throws SQLException {
        String query = "Select * from user";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<User> users = new ArrayList<User>();
        while (resultSet.next()) {
            int id = resultSet.getInt("user_id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            users.add(new User(username,password,id));
        }
        return users;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        for (User u: getUsers()
             ) {
            if (u.getUserId()==id){
                return u;
            }

        }
        return null;
    }
}

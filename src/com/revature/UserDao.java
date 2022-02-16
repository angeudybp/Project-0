package com.revature;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void addUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
    void isLogged(User user) throws SQLException;
    public void updateLoginUser(User user, int i) throws SQLException;
    List<User> getUsers() throws SQLException;

    User getUserById(int id) throws SQLException;
}

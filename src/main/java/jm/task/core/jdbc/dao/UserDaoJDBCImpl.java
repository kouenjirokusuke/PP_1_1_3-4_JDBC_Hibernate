package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Connection connection = Util.getConnection();
            ResultSet tables = connection.getMetaData().getTables("katapp114", null, "users", new String[] {"TABLE"});

            if (!tables.next()) {
                connection.prepareStatement("CREATE TABLE users (Id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(32), last_name VARCHAR(32), age TINYINT)").executeUpdate();
                System.out.println("table created");
            } else {
                System.out.println("Table 'users' already exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Connection connection = Util.getConnection();
            ResultSet tables = connection.getMetaData().getTables(null, null, "users", new String[] {"TABLE"});

            if (tables.next()) {
                connection.prepareStatement("DROP TABLE users").executeUpdate();
            } else {
                System.out.println("Table 'users' doesn't exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Connection connection = Util.getConnection();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement statement = Util.getConnection().prepareStatement("DELETE FROM users WHERE id=?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            Connection connection = Util.getConnection();
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM users").executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void cleanUsersTable() {
        try {
            Util.getConnection().prepareStatement("TRUNCATE TABLE users").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

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
        try (Connection connection = Util.getConnection()) {
            ResultSet tables = connection.getMetaData().getTables(null, null, "users", new String[] {"TABLE"});

            if (!tables.next()) {
                connection.createStatement().executeUpdate("CREATE TABLE users (Id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(32), last_name VARCHAR(32), age TINYINT)");
            } else {
                System.out.println("Table 'users' already exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            ResultSet tables = connection.getMetaData().getTables(null, null, "users", new String[] {"TABLE"});

            if (tables.next()) {
                connection.createStatement().executeUpdate("DROP TABLE users");
            } else {
                System.out.println("Table 'users' doesn't exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().executeUpdate(String.format("INSERT INTO users (name, last_name, age)" +
                    " VALUES ('%s', '%s', '%d')", name, lastName, age));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().executeUpdate(String.format("DELETE FROM users WHERE id='%d'", id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users");

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
        try (Connection connection = Util.getConnection()) {
            connection.createStatement().executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

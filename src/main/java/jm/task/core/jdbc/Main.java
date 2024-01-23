package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Alexandr", "Petrov", (byte) 30);
        userService.saveUser("Alexey", "Zhirkov", (byte) 25);
        userService.saveUser("Andrey", "Potapov", (byte) 20);
        userService.saveUser("Anton", "Gerasimov", (byte) 15);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.getConnection().close();
    }
}

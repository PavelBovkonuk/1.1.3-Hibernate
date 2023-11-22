package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Geralt", "from Rivia", (byte) 100);
        userService.saveUser("Frodo", "Baggins", (byte) 25);
        userService.saveUser("Gandalf", "the White", (byte) 80);
        userService.saveUser("Peter", "Parker", (byte) 18);

        userService.getAllUsers().forEach(user-> System.out.println(user));
        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.closeConnection(Util.getConnection());
    }
}
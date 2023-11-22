package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Util() {

    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соединение успешно!");
        } catch (SQLException e) {
            System.out.println("Ошибка соединения!");
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение закрыто!");
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия соединения");
                e.printStackTrace();
            }
        }
    }

}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String createQuery = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(50)," +
                "lastName VARCHAR(50)," +
                "age TINYINT," +
                "PRIMARY KEY (id)" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createQuery);
            System.out.println("Таблица создана!");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы!");
        }
    }

    @Override
    public void dropUsersTable() {
        String dropQuery = "DROP TABLE IF EXISTS users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(dropQuery)) {
            preparedStatement.execute();
            System.out.println("Таблица удалена!");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы!");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String saveQuery = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User " + name + " " + lastName + " добавлен!");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Произошла ошибка добавления user!");
        }
    }

    @Override
    public void removeUserById(long id) {
        String removeQuery = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeQuery)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User удален!");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Произошла ошибка удаления user!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String readQuery = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(readQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            connection.setAutoCommit(false);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Ошибка чтения!");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String cleanQuery = "TRUNCATE TABLE users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(cleanQuery)) {
            preparedStatement.execute();
            System.out.println("Таблица очищена!");
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы!");
            e.printStackTrace();
        }
    }
}

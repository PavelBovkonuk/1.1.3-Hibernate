package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createQuery = "CREATE TABLE users (" +
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

    public void dropUsersTable() {
        String dropQuery = "DROP TABLE users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(dropQuery)) {
            preparedStatement.execute();
            System.out.println("Таблица удалена!");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы!");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveQuery = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User добавлен!");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка добавления user!");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeQuery = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            System.out.println("User удален!");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка удаления user!");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String readQuery = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(readQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка чтения!");
            e.printStackTrace();
        }
        return users;
    }

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

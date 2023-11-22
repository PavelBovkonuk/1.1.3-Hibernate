package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session = Util.getSessionFactory().openSession();

    public UserDaoHibernateImpl() {

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
        try {
            session.beginTransaction();
            session.createSQLQuery(createQuery).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана!");
        } catch (Exception e) {
            System.out.println("Ошибка создания таблицы!");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String dropQuery = "DROP TABLE IF EXISTS users";
        try {
            session.beginTransaction();
            session.createSQLQuery(dropQuery).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена!");
        } catch (Exception e) {
            System.out.println("Ошибка удаления таблицы!");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User " + name + " " + lastName + " добавлен!");
        } catch (Exception e) {
            System.out.println("Ошибка добавления пользователя!");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("User удален!");

            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка уаления пользователя!");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            list = session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            System.out.println("Ошибка при получении всех пользователей: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена!");
        } catch (Exception e) {
            System.out.println("Ошибка очистки таблицы!");
            e.printStackTrace();
        }
    }
}

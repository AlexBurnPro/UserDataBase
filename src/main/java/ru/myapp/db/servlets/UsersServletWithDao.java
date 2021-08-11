package ru.myapp.db.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.myapp.db.dao.UsersDao;
import ru.myapp.db.dao.UsersDaoJdbcImpl;
import ru.myapp.db.dao.UsersDaoJdbcTemplateImpl;
import ru.myapp.db.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


/**
 *  получение списка всех зарегистрированных пользователей /allUsers
 *  что бы вывести список рользователей из БД нужен слой DAO - механизм доступа к данным
 *
 *  реализация подключения к БД на JDBC (UsersDaoJdbcImpl)
 *      или с помощью SpringJDBC (UsersDaoJdbcTemplateImpl)
 */
@WebServlet("/users")
public class UsersServletWithDao extends HttpServlet {

    private UsersDao usersDao;

    /**
     * @DriverManagerDataSource предоставляет Spring объект connection к БД
     *     передается в конструктор класса UsersDaoJdbcImpl
     *     забираем настройки конфигурации из файла db.properties c помощью Properties
     */
    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        try {
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String dbDriverClassName = properties.getProperty("db.driverClassName");

            dataSource.setUsername(dbUsername);
            dataSource.setUrl(dbUrl);
            dataSource.setPassword(dbPassword);
            dataSource.setDriverClassName(dbDriverClassName);

            /*
             *  на выбор 2 реализации
             *      UsersDaoJdbcTemplateImpl - под капотом SpringJDBC
             *      UsersDaoJdbcImpl на чистом JDBC
             */
            usersDao = new UsersDaoJdbcTemplateImpl(dataSource);
        } catch (IOException e){
            throw new IllegalStateException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = usersDao.findAll();
        req.setAttribute("usersFromServer", users);
        req.getServletContext().getRequestDispatcher("/jsp/users.jsp").forward(req,resp);
    }
}
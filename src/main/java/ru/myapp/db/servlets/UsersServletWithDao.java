package ru.myapp.db.servlets;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.myapp.db.dao.UsersDao;
import ru.myapp.db.dao.UsersDaoJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@WebServlet("/allUsers")
public class UsersServletWithDao extends HttpServlet {

    private UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();    //Spring. объект предоставляет connection к БД

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

            usersDao = new UsersDaoJdbcImpl(dataSource);
        } catch (IOException e){
            throw new IllegalStateException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}

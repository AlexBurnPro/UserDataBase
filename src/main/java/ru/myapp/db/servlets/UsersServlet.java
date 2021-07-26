package ru.myapp.db.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private Connection connection;

    /**
     * создаем соедеинение с БД
     * @throws ServletException
     *    забираем настройки конфигурации из файла db.properties c помощью Properties
     *    драйвер jdbc загружаем с помощью Reflection
     *    создаем Connection
     */
    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream(getServletContext().getRealPath("/WEB-INF/classes/db.properties")));
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String dbDriverClassName = properties.getProperty("db.driverClassName");

            Class.forName(dbDriverClassName);
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new IllegalStateException();
        }
    }

    /**
     *   на эту страницу пользователь вводит свои данные
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/addUser.jsp").forward(req,resp);
    }

    /**
     *
     *     <form method="post" action="/users"> данные с этой страницы уйдут POST запросом,
     *     из форм попадут в переменные
     *     затем будет создан объект Statement, который выполнит SQL запрос в БД на добавление нового user
     *     используя данные из форм
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");

        try {
            Statement statement = connection.createStatement();
            String sqlInsert = "INSERT INTO fix_user(first_name, last_name)" +
                    "VALUES('" + firstName + "','" + lastName + "');";
            statement.execute(sqlInsert);

        } catch(SQLException e) {
            throw new IllegalStateException();
        }
    }
}

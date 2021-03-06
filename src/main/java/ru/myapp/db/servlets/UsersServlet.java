package ru.myapp.db.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 *  добавление пользователей в БД на странице /addUser
 *
 *  реализация подключения к БД на чистом JDBC
 *  @WebServlet("/addUser") указывает Tomcat какой запрос на какой сервлет отправить
 *  сервлет выводит страницу для регистрации (добавления) пользователя в БД (GET)
 *  и получает данные пользователя (POST)
 *
 *  т.е что бы занести данные в БД слой DAO не нужен:
 *      1. Коннектимся к БД (урл логин пароль JDBC драйвер)
 *      2. Получаем данные из POST запроса
 *      3. отправляем SQL c данными в БД
 */

@WebServlet("/addUser")
public class UsersServlet extends HttpServlet {

    /*
     * экземпляр класса Connection - это собственно, подключение к БД
     */
    private Connection connection;

    /**
     * создаем соединение с БД через DriverManager
     *    DriverManager является уровнем управления JDBC,
     *    отслеживает все доступные драйверы и управляет установлением соединений между БД и соответствующим драйвером
     *
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

            /*
             * указание диспетчеру драйверов JDBC, какой именно драйвер загрузить
             */
            Class.forName(dbDriverClassName);

            /*
             * в JDBC существует два способа подключения к БД: Через DataSource и через DriverManager
             *
             *      1. доступ к БД через connection и DataSource (параметры URL, username, password)
             *         через конструктор коасса реализующего интерфейс DAO
             *         this.connection = dataSource.getConnection();
             *
             *         а так же используя JdbcTemplate который предоставляет Spring Jdbc
             *         this.jdbcTemplate = new JdbcTemplate(dataSource);
             *
             *      2. доступ к БД через DriverManager (параметры URL, username, password)
             *         connection = DriverManager.getConnection(Url, Username, Password);
             *
             *         ранее должен быть загруже класс драйвера БД
             *         Class.forName(dbDriverClassName);
             */
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new IllegalStateException();
        }
    }

    /**
     *   addUser.jsp - страница появляется в ответ на GET-запрос /addUser
     *   на эту страницу пользователь вводит свои данные
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/jsp/addUser.jsp").forward(req,resp);
    }

    /**
     *     <form method="post" action="/addUser"> данные с этой страницы придут POST запросом,
     *     из форм попадут в переменные
     *     затем будет создан объект PrepareStatement, который выполнит SQL запрос в БД на добавление нового user
     *     используя данные из форм
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");

        try {
            /*
             * данный запрос небезопасен, т.к. использует строку и конкатенацию строк в SQL - запросе
             * можно  полем firstName отправить строку как SQL иньекцию, например
             * temp','temp');DROP TABLE fix_car;SELECT('temp
             *
             * в результате таблица fix_car будет удалена
             *

            Statement statement = connection.createStatement();
            String sqlInsert = "INSERT INTO fix_user(first_name, last_name)" +
                    "VALUES('" + firstName + "','" + lastName + "');";
            statement.execute(sqlInsert);

             */

            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO fix_user(first_name, last_name) VALUES (?, ?)");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            /*
             *  prepareStatement.executeQuery() - запрос, результатом которого является
             *      один единственный набор значений, такого как запрос типа SELECT, получаем ResultSet
             *
             *  prepareStatement.execute() - используется, когда операторы SQL
             *      возвращают более одного набора данных, более одного счетчика обновлений или и то, и другое
             *
             *  prepareStatement.executeUpdate() -  используется для выполнения операторов управления данными
             *      типа INSERT, UPDATE или DELETE (DML - Data Manipulation Language),
             *      для операторов определения структуры БД - CREATE TABLE, DROP TABLE (DDL- Data Definition Language)
             *      Результатом выполнения операторов INSERT, UPDATE, или DELETE является изменения
             *      одной или более строк таблицы.
             */
            preparedStatement.execute();

        } catch(SQLException e) {
            throw new IllegalStateException();
        }
    }
}

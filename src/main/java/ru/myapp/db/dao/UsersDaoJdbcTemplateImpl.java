package ru.myapp.db.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.myapp.db.models.Car;
import ru.myapp.db.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.*;

/**
 *  реализация подключения к БД на Spring JDBC
 *
 *  JdbcTemplate - слой между чистым JDBC и бизнес-логикой, дает полный контроль над SQL-запросом
 *      Connection, PrepareStatement и прочие под капотом
 *
 */

public class UsersDaoJdbcTemplateImpl implements UsersDao {

    private JdbcTemplate jdbcTemplate;
    private Map<Integer, User> userMap = new HashMap<>();

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM fix_user";

    //language=SQL
    private final String SQL_SELECT_BY_FIRST_NAME =
            "SELECT * FROM fix_user WHERE first_name = ?";

    //language=SQL
    private final String SQL_SELECT_USERS_WITH_CAR =
            "SELECT fix_user.*, fix_car.id as car_id, fix_car.model " +
                    "FROM fix_user LEFT JOIN fix_car ON fix_user.id = fix_car.owner_id WHERE fix_user.id = ?";

    /**
     *   SpringJDBC RowMapper используется JdbcTemplate для отображения строк ResultSet для каждой строки,
     *   обрабатывает отдельно каждую запись, полученную из БД,
     *   и возвращает уже готовый объект - модель данных
     *   Каждую строку здесь обрабатываем отдельно при помощи ResultSet.
     *   отображает строку i объекта ResultSet в объект User
     *      т.е. правило, по которому строки ResultSet преобразуется в объект User
     *      замена конструкции на чистом JDBC с List, ResultSet и while
     *
     *  получаем объект RowMapper для передачи в метод findAll() найти объекты User
     */
    private RowMapper<User> userRowMapper
            = (ResultSet resultSet, int i) -> {
        return new User(

                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"));
    };

    /**
     *  получаем объект RowMapper для передачи в метод find() найти User и их Car
     */
    private RowMapper<User> userRowMapperForMap
            = (ResultSet resultSet, int i) -> {

                Integer id = resultSet.getInt("id");

                if(!userMap.containsKey(id)) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    User user = new User(id, firstName, lastName, new ArrayList<>());
                    userMap.put(id, user);
                }

                Car car = new Car(resultSet.getInt("id"),
                        resultSet.getString("model"),
                        userMap.get(id));
                userMap.get(id).getCars().add(car);

                return userMap.get(id);
    };

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
    public UsersDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return jdbcTemplate.query(SQL_SELECT_BY_FIRST_NAME, userRowMapper, firstName);
    }

    @Override
    public Optional<User> find(Integer id) {
        jdbcTemplate.query(SQL_SELECT_USERS_WITH_CAR, userRowMapperForMap, id);

        if(userMap.containsKey(id)) {
            return Optional.of(userMap.get(id));
        }
        return Optional.empty();
    }

    @Override
    public void save(User model) {

    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }
}

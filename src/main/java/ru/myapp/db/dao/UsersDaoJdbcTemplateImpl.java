package ru.myapp.db.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.myapp.db.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 *  реализация подключения к БД на Spring JDBC
 *
 *  JdbcTemplate - слой между чистым JDBC и бизнес-логикой, дает полный контроль над SQL-запросом
 *      Connection, PrepareStatement и прочие под капотом
 */

public class UsersDaoJdbcTemplateImpl implements UsersDao {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM fix_user";

    /**
     *   SpringJDBC RowMapper отображает строку i объекта ResultSet в объект User
     *      т.е. правило, по которому строки ResultSet преобразуется в объект User
     *      получаем объект RowMapper для передачи в метод findAll()
     *
     *      замена конструкции на чистом JDBC с List, ResultSet и while
     */
    private RowMapper<User> userRowMapper
            = (resultSet, i) -> {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"));
    };

    public UsersDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAllByFirstName(String firstName) {
        return null;
    }

    @Override
    public Optional<User> find(Integer id) {
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

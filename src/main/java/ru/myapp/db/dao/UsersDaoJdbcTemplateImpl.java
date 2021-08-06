package ru.myapp.db.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.myapp.db.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 *  JdbcTemplate слой между чистым JDBC и бизнес-логикой
 */

public class UsersDaoJdbcTemplateImpl implements UsersDao {

    private JdbcTemplate jdbcTemplate;

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
        return null;
    }
}

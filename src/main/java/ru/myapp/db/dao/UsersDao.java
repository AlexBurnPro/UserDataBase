package ru.myapp.db.dao;

import ru.myapp.db.models.User;

import java.util.List;

public interface UsersDao extends CrudDao<User> {
    List<User> findAllByFirstName(String firstName);
}
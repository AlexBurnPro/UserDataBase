package ru.myapp.db.dao;

import java.util.List;
import java.util.Optional;

/**
 *
 *  DAO (Data Access Objects) - шаблон проектирования, определяющий механизм доступа к данным.
 *  задача шаблона DAO - построить мост между реляционной и объектной моделями данных
 *  Слой DAO отделяет слой бизнес-логики (системы) от слоя БД,
 *  определяет общие методы использования соединения с БД (взаимодействия с БД),
 *  которые будут описаны в интерфейсе
 *  в общем, DAO — это класс (абстрактный, или интерфейс), содержащий CRUD-методы для конкретной сущности
 *
 */

public interface CrudDao <T> {

    /* это общий интерфейс
     * самые базовые операции в системе
     * собственно CRUD
     */
    Optional<T> find(Integer id);
    void save(T model);
    void update(T model);
    void delete(Integer id);

    List<T> findAll();
}

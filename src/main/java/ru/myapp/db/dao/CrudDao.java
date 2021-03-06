package ru.myapp.db.dao;

import java.util.List;
import java.util.Optional;

/**
 * немного теории хранения и обработки информации
 *
 *  Data Tier или "Уровень данных", включает в себя место хранения данных
 *  (например, SQL базу данных) и средства для работы с хранилищем данных (например, JDBC)
 *  из Data Tier выделяется дополнительный слой — Persistence Layer.
 *  Data Tier — это уровень хранения самих данных,
 *  Persistence Layer - это слой абстракции для работы с данными из хранилища с уровня Data Tier.
 *
 *  К уровню Persistence Layer можно отнести шаблон DAO
 *
 *  DAO (Data Access Objects) - шаблон проектирования, определяющий механизм доступа к данным.
 *  задача шаблона DAO - построить мост между реляционной и объектной моделями данных
 *  Слой DAO отделяет слой бизнес-логики (системы) от слоя БД,
 *  определяет общие методы использования соединения с БД (взаимодействия с БД),
 *  которые будут описаны в интерфейсе
 *  в общем, DAO — это класс (абстрактный, или интерфейс), содержащий CRUD-методы для конкретной сущности в БД
 *
 */

public interface CrudDao <T> {

    /* это общий интерфейс
     * самые базовые операции в системе
     * собственно CRUD
     */

    Optional<T> find(Integer id);     //вместо null возвращается пустой объект Optional.empty
    void save(T model);
    void update(T model);
    void delete(Integer id);

    List<T> findAll();
}

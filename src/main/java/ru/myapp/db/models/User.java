package ru.myapp.db.models;

import java.util.List;

/**
 *  отображение таблиц в models
 */

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private List<Car> cars;

    public User() {
    }

    public User(int id, String firsName, String lastName) {
        this.id = id;
        this.firstName = firsName;
        this.lastName = lastName;
    }

    public User(String firsName, String lastName) {
        this.firstName = firsName;
        this.lastName = lastName;
    }

    public User(int id, String firstName, String lastName, List<Car> cars) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cars = cars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firsName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}

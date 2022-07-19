package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    void removeUser(int id);
    void updateUser(int id, User user);
    User findUserById(int id);
    List<User> showUsers();
    User findByUsername(String username);
}
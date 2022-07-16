package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    void removeUser(int id);
    void updateUser(int id, User user);
    User findUserById(int id);
    List<User> showUsers();

}

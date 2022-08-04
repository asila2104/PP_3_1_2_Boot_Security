package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public List<User> getAllUsers() {
        return userService.showUsers();
    }

    @GetMapping("/user")
    public User showUserForUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @PostMapping("/add")
    public void createUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PatchMapping("/edit/{id}")
    public void updateUser(@RequestBody User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.removeUser(id);
    }

}

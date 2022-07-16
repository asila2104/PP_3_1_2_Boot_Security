package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.showUsers());

        return "users";
    }

    @GetMapping("/user")
    public String showUser(Principal principal) {

        return "user";
    }

    @GetMapping("/admin")
    public String showAdmin() {
        return "admin";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin/users";
    }

    @PatchMapping("/admin/{id}")
    public String editUser(@PathVariable("id") int id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }
}
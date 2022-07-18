package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;

import javax.transaction.Transactional;

@Component
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String username);
}

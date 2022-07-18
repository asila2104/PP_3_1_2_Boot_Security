package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class UserDAOImp implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final RoleDao roleDao;

    public UserDAOImp(BCryptPasswordEncoder bCryptPasswordEncoder, RoleDao roleDao) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> showUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    @Override
    public User findByUsername(String username) {
        return entityManager.createQuery("SELECT user FROM User user where user.username = :username",
                User.class).setParameter("username", username).getSingleResult();
    }

    @Override
    public void addUser(User user) {
        Role newRole = roleDao.findByRole("ROLE_USER");

        user.setRoles(Collections.singleton(newRole));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        entityManager.persist(user);
    }

    @Override
    public void removeUser(int id) {
        entityManager.remove(findUserById(id));

    }

    @Override
    public void updateUser(int id, User user) {
        User user1 = findUserById(id);
        user1.setUsername(user.getUsername());
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        addUser(user1);
    }

    @Override
    public User findUserById(int id) {
        return entityManager.find(User.class, id);
    }


}

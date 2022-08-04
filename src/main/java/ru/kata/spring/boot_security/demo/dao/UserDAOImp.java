package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return entityManager.createQuery("SELECT user FROM User user where user.email = :username",
                User.class).setParameter("username", username).getSingleResult();
    }

    @Override
    public void addUser(User user) {
        user.setRoles(updateRoles(user));
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
        user1.setId(id);
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setFirstName(user.getFirstName());

        if (user.getRoles() != null) {
            user1.setRoles(updateRoles(user));
        }
        user1.setAge(user.getAge());
        if (user.getPassword() != null) {
            user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        entityManager.persist(user1);
    }

    @Override
    public User findUserById(int id) {
        return entityManager.find(User.class, id);
    }

    public Set<Role> updateRoles(User user) {
        Set<Role> set1 = new HashSet<>();

        for (Role role : user.getRoles()) {
            Role newRole = roleDao.findByRole(role.getRole());
            set1.add(newRole);
        }

        return set1;
    }
}

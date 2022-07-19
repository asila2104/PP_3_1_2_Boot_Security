package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Set;

@Component
@Transactional
public class RoleDaoImp implements RoleDao {

    private final EntityManager entityManager;

    public RoleDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findByRole(String role) {
        return entityManager.createQuery("SELECT role FROM Role role where role.role = :role",
                Role.class).setParameter("role", "ROLE_" + role).getSingleResult();

    }

}
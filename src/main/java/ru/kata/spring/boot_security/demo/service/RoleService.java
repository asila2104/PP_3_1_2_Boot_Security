package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleService {
    final RoleRepository dao;

    public RoleService(RoleRepository dao) {
        this.dao = dao;
    }

    public Role findRoleByName(String name) {
        return dao.findByRole(name);
    }
}

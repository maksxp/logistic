package ua.viasat.logistic.service;


import ua.viasat.logistic.model.Role;

import java.util.List;

public interface RoleService {
    Role findById(int id) ;
    List<Role> listAllRoles();
    Role findByRole(String role);
}

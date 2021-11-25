package com.fluxchallenge.fluxemployeesapi.dao;

import com.fluxchallenge.fluxemployeesapi.dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}

package com.fluxchallenge.fluxemployeesapi.dao;

import com.fluxchallenge.fluxemployeesapi.dto.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUsersDao extends JpaRepository<AppUser, Long> {

    public AppUser findByName(String name);
}

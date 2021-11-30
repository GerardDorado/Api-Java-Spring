package com.fluxchallenge.fluxemployeesapi.service;

import com.fluxchallenge.fluxemployeesapi.dto.AppUser;
import com.fluxchallenge.fluxemployeesapi.dto.Role;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void assignRole(String userName, String role) throws Exception;
    AppUser getUser(String name);
    Role getRole(String name);
}

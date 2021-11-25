package com.fluxchallenge.fluxemployeesapi.service;

import com.fluxchallenge.fluxemployeesapi.dao.AppUsersDao;
import com.fluxchallenge.fluxemployeesapi.dao.RoleDao;
import com.fluxchallenge.fluxemployeesapi.dto.AppUser;
import com.fluxchallenge.fluxemployeesapi.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUsersDao appUsersDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public AppUser saveUser(AppUser user) {
        return appUsersDao.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleDao.save(role);
    }

    @Override
    public void assignRole(String userName, String role) throws Exception {
        AppUser user = this.getUser(userName);
        for(Role userRole : user.getRoles()) {
            if(userRole.getName().equals(role)) {
                throw new Exception("Ya esta asignado el rol a este usuario");
            }
        }
        Role newRole = roleDao.findByName(role);
        Collection<Role> roles = user.getRoles();
        roles.add(newRole);
        user.setRoles(roles);
    }

    @Override
    public AppUser getUser(String name) {
        return appUsersDao.findByName(name);
    }

    @Override
    public Role getRole(String name) {
        return roleDao.findByName(name);
    }
}

package com.fluxchallenge.fluxemployeesapi.service;

import com.fluxchallenge.fluxemployeesapi.dao.AppUsersDao;
import com.fluxchallenge.fluxemployeesapi.dao.RoleDao;
import com.fluxchallenge.fluxemployeesapi.dto.AppUser;
import com.fluxchallenge.fluxemployeesapi.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    @Autowired
    private AppUsersDao appUsersDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(AppUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser user = appUsersDao.findByName(userName);
        if (user == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }
}

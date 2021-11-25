package com.fluxchallenge.fluxemployeesapi.controller;

import com.fluxchallenge.fluxemployeesapi.dto.AppUser;
import com.fluxchallenge.fluxemployeesapi.dto.Role;
import com.fluxchallenge.fluxemployeesapi.service.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/AppUser")
public class AppUserController {

    @Autowired
    AppUserServiceImpl service;
    
    @GetMapping("/User")
    ResponseEntity<AppUser> getUser(String userName){
        try {
            return ResponseEntity.ok().body(service.getUser(userName));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/User")
    ResponseEntity<String> saveUser(AppUser user){
        try {
            service.saveUser(user);
            return ResponseEntity.ok().body("Nuevo User Creado");
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/Role")
    ResponseEntity<Role> getRole(String roleName){
        try {
            return ResponseEntity.ok().body(service.getRole(roleName));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/Role")
    ResponseEntity<String> saveRole(Role role){
        try {
            service.saveRole(role);
            return ResponseEntity.ok().body("Nuevo Rol Creado");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

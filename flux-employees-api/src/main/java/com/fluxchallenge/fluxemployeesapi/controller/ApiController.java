package com.fluxchallenge.fluxemployeesapi.controller;

import com.fluxchallenge.fluxemployeesapi.dto.User;
import com.fluxchallenge.fluxemployeesapi.dto.UserBasico;
import com.fluxchallenge.fluxemployeesapi.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/App")
public class ApiController {

    @Autowired
    UserServiceImpl service;

    @ResponseBody
    @GetMapping("/AllUsers")
    ResponseEntity<List<UserBasico>> getListOfUsers(@RequestParam int beginIndex, @RequestParam int endIndex, @RequestBody(required = false) Map<String, String> filters) {
        try {
            List<UserBasico> users = service.getListOfUsers(filters, beginIndex, endIndex);
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/User")
    ResponseEntity<User> getFullUser(@RequestParam String dni) {
        return ResponseEntity.ok(service.getFullUser(dni));
    }

    @PostMapping("/User")
    ResponseEntity<String> insertUser(@RequestBody User newUser) {
        try {
            service.insertUser(newUser);
            return ResponseEntity.ok("Se agrego el usuario correctamente");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Ya existe el usuario");
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese dni");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Error");
        }
    }

    @DeleteMapping("/User")
    ResponseEntity<String> removeUser(@RequestParam String dni) {
        try {
            service.deleteUser(dni);
            return ResponseEntity.ok("Se elimino el usuario correctamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Error");
        }
    }

    @PutMapping("/User")
    ResponseEntity<String> updateUser(@RequestBody User modifiedUser) {
        try {
            service.updateUser(modifiedUser);
            return ResponseEntity.ok("Se actualizo el usuario correctamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Error");
        }
    }
}

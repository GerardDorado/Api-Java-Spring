package com.fluxchallenge.fluxemployeesapi.controller;

/*import com.fluxchallenge.auth.JwtRequest;
import com.fluxchallenge.auth.JwtResponse;
import com.fluxchallenge.auth.JwtTokenUtil;
import com.fluxchallenge.auth.JwtUserDetailsService;*/
import com.fluxchallenge.fluxemployeesapi.dto.LoginForm;
import com.fluxchallenge.fluxemployeesapi.dto.User;
import com.fluxchallenge.fluxemployeesapi.dto.UserBasico;
import com.fluxchallenge.fluxemployeesapi.service.UserServiceImpl;

import com.fluxchallenge.fluxemployeesapi.utils.CredentialManager;
import org.springframework.beans.factory.annotation.Autowired;

/*import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;*/

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class ApiController {

/*
    Genera error, aun siguiendo esta guideline https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide#authenticationmanager-bean
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;



    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }*/

    static String token = ""; //Como no pude implementar el jwt con spring security genero un token en el controller para cumplir la consigna

    @Autowired
    UserServiceImpl service;

    @Autowired
    CredentialManager credentialManager;

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginForm loginForm){
        try {
            if (credentialManager.authentication(loginForm)) { //Si las credenciales son correctas genero un string de 40 caracteres alfanumericos (un token)
                Random random = new Random();
                token = random.ints(48, 122 + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(40)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                return ResponseEntity.ok().body(token);
            } else {
                return ResponseEntity.badRequest().body("No existe el usuario o ingresaste mal la contraseña");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ingresaste mal las credenciales");
        }
    }

    @ResponseBody
    @GetMapping("/AllUsers")
    ResponseEntity<List<UserBasico>> getListOfUsers(@RequestHeader String authorization, @RequestParam int beginIndex, @RequestParam int endIndex, @RequestBody Map<String,String> filters) {
        if(authorization.equals(token)) {
            return ResponseEntity.ok(service.getListOfUsers(filters, beginIndex, endIndex));
        } else {
            return (ResponseEntity.badRequest().body(null));
        }
    }

    @GetMapping("/User")
    ResponseEntity<User> getFullUser(@RequestHeader String authorization, @RequestParam String dni) {
        if(authorization.equals(token)) {
            return ResponseEntity.ok(service.getFullUser(dni));
        } else {
            return (ResponseEntity.badRequest().body(null));
        }

    }

    @PostMapping("/User")
    ResponseEntity<String> insertUser(@RequestHeader String authorization , @RequestBody User newUser) {
        if(authorization.equals(token)) {
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
        } else {
            return (ResponseEntity.badRequest().body("Token invalido"));
        }
    }

    @DeleteMapping("/User")
    ResponseEntity<String> removeUser(@RequestHeader String authorization, @RequestParam String dni) {
        if(authorization.equals(token)) {
            try {
                service.deleteUser(dni);
                return ResponseEntity.ok("Se elimino el usuario correctamente");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.internalServerError().body("Error");
            }
        } else {
            return (ResponseEntity.badRequest().body("Token invalido"));
        }
    }

    @PostMapping("/existingUser")
    ResponseEntity<String> updateUser(@RequestHeader String authorization, @RequestBody User modifiedUser) {
        if(authorization.equals(token)) {
            try{
                service.updateUser(modifiedUser);
                return ResponseEntity.ok("Se actualizo el usuario correctamente");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.internalServerError().body("Error");
            }
        } else {
            return (ResponseEntity.badRequest().body("Token invalido"));
        }
    }
}

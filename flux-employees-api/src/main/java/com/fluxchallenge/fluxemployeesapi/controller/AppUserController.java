package com.fluxchallenge.fluxemployeesapi.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluxchallenge.fluxemployeesapi.dto.AppUser;
import com.fluxchallenge.fluxemployeesapi.dto.Role;
import com.fluxchallenge.fluxemployeesapi.service.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    ResponseEntity<String> saveUser(@RequestBody AppUser user){
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
    ResponseEntity<String> saveRole(@RequestBody Role role){
        try {
            service.saveRole(role);
            return ResponseEntity.ok().body("Nuevo Rol Creado");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/RefreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = service.getUser(username);

                String access_token = JWT.create()
                        .withSubject(user.getName())
                        .withExpiresAt(new Date(System.currentTimeMillis() * 1 * 60 * 1000))
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e){
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> message = new HashMap<>();
                message.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

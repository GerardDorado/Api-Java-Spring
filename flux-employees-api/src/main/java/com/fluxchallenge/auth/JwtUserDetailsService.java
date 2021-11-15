package com.fluxchallenge.auth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if("Gerardo-Flux".equals(userName)){
            return new User("Gerardo-Flux","$2y$10$31XN7oALQCFgMcheepJ8Q.r9Uhkp16MdzHtogAj/B4Nz0sMq1JU9O", new ArrayList<>()); //Password Flux12345
        } else {
            throw new UsernameNotFoundException("Usuario: " + userName + " no encontrado");
        }
    }
}

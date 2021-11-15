package com.fluxchallenge.fluxemployeesapi.utils;

import com.fluxchallenge.fluxemployeesapi.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class CredentialManager {
    @Autowired
    private Environment env;

    public String getUser() {
        return env.getProperty("auth.user");
    }

    public String getPassword() {
        return env.getProperty("auth.password");
    }

    public boolean authentication(LoginForm loginForm) throws Exception {
        if(loginForm.getUser() == null || loginForm.getPassword() == null) {throw new Exception();}
        return (loginForm.getUser().equals(this.getUser()) && loginForm.getPassword().equals(this.getPassword()));
    }
}



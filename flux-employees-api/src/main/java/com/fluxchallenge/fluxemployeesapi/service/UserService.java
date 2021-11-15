package com.fluxchallenge.fluxemployeesapi.service;

import com.fluxchallenge.fluxemployeesapi.dto.User;
import com.fluxchallenge.fluxemployeesapi.dto.UserBasico;

import java.util.List;
import java.util.Map;

public interface UserService {

    public void insertUser(User newUser);
    public void deleteUser(String id);
    public User getFullUser(String dni);
    public List<UserBasico> getListOfUsers(Map<String,String> filter, int beginIndex, int endIndex);
    public void updateUser(User modifiedUser);
}

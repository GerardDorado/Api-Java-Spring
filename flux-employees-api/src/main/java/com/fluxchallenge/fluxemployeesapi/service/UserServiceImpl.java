package com.fluxchallenge.fluxemployeesapi.service;

import com.fluxchallenge.fluxemployeesapi.dao.UsersDao;
import com.fluxchallenge.fluxemployeesapi.dto.User;
import com.fluxchallenge.fluxemployeesapi.dto.UserBasico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UsersDao dao;

    @Override
    public void insertUser(User newUser) {
        dao.save(newUser);
    }

    @Override
    public void deleteUser(String dni) {
        User user = dao.findByDni(dni);
        dao.deleteById(user.getId());
    }

    @Override
    public User getFullUser(String dni) {
        return dao.findByDni(dni);
    }

    @Override
    public List<UserBasico> getListOfUsers(Map<String,String> filter, int beginIndex, int endIndex) throws Exception {
        List<UserBasico> result = new ArrayList<>();
        if(filter == null) {
            result = dao.findAllBasicUsers(PageRequest.of(beginIndex, endIndex));
        } else if(filter.containsKey("dni")) {
            result = dao.findByDni(filter.get("dni"), PageRequest.of(beginIndex, endIndex));
        } else if((filter.containsKey("name")) && (filter.containsKey("surname"))) {
            result = dao.findByNameAndSurnameLike(filter.get("name"),filter.get("surname"),PageRequest.of(beginIndex, endIndex));
        } else {
            throw new Exception("Bad Filter");
        }
        return result;
    }

    @Override
    public void updateUser(User modifiedUser) {
        User user = dao.findByDni(modifiedUser.getDni());
        modifiedUser.setId(user.getId());
        dao.save(modifiedUser);
    }
}

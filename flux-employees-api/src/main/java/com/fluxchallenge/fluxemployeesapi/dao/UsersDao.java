package com.fluxchallenge.fluxemployeesapi.dao;


import com.fluxchallenge.fluxemployeesapi.dto.User;
import com.fluxchallenge.fluxemployeesapi.dto.UserBasico;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersDao extends JpaRepository<User, Long> {
    @Query("SELECT new com.fluxchallenge.fluxemployeesapi.dto.UserBasico (name, surname, dni) FROM User u WHERE u.dni LIKE %:dni% ")
    public List<UserBasico> findByDni(@Param("dni") String dni, Pageable pageable);

    @Query(value="SELECT new com.fluxchallenge.fluxemployeesapi.dto.UserBasico (name, surname, dni) FROM User u where u.name LIKE %:name% AND u.surname LIKE %:surname%")
    public List<UserBasico> findByNameAndSurnameLike(@Param("name")String name, @Param("surname") String surname, Pageable pageable);

    @Query(value = "SELECT new com.fluxchallenge.fluxemployeesapi.dto.UserBasico (name, surname, dni) FROM User ")
    public List<UserBasico> findAllBasicUsers(Pageable pageable);

    public User findByDni(String dni);

}

package com.example.usermanagement.dao;

import com.example.usermanagement.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, String> {
    void deleteByUserName(String userName);
    boolean existsByUserName(String userName);

    Optional<User> findByUserName(String username);


}

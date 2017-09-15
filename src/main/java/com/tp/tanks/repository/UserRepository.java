package com.tp.tanks.repository;


import com.tp.tanks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByUsername(String username);
}
package com.company.mini_grocery.repository;

import com.company.mini_grocery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long>{

   Optional<User> findByEmail(String email);
}

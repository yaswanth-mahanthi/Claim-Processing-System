package com.yaswanth.claimflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yaswanth.claimflow.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

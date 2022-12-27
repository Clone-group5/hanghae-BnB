package com.hanghae.hanghaebnb.users.repository;

import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}

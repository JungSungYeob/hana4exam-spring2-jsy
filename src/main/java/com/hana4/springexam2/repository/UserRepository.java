package com.hana4.springexam2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hana4.springexam2.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

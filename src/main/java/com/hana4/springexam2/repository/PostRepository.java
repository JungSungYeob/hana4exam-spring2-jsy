package com.hana4.springexam2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana4.springexam2.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}

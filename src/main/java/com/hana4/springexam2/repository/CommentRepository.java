package com.hana4.springexam2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana4.springexam2.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

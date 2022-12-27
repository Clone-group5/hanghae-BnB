package com.hanghae.hanghaebnb.comment.repository;

import com.hanghae.hanghaebnb.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

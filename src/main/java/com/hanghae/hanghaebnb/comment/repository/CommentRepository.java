package com.hanghae.hanghaebnb.comment.repository;

import com.hanghae.hanghaebnb.comment.entity.Comment;
import com.hanghae.hanghaebnb.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.room = :room")
    void deleteByRoom(@Param("room") Room room);

}

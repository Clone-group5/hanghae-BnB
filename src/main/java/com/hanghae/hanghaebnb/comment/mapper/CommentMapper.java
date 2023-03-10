package com.hanghae.hanghaebnb.comment.mapper;




import com.hanghae.hanghaebnb.comment.dto.RequestComment;
import com.hanghae.hanghaebnb.comment.dto.ResponseComment;
import com.hanghae.hanghaebnb.comment.entity.Comment;
import com.hanghae.hanghaebnb.room.entity.Room;

import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    public Comment toComment(Users users, RequestComment requestComment, Room room){
        return Comment.builder()
                .users(users)
                .writer(users.getNickname())
                .contents(requestComment.getContents())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .room(room)
                .build();
    }

    public ResponseComment toResponseComment(Comment comment){
        return ResponseComment.builder()
                .commentId(comment.getCommentId())
                .writer(comment.getWriter())
                .contents(comment.getContents())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }



}
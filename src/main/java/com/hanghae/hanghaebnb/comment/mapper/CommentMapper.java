package com.hanghae.hanghaebnb.comment.mapper;




import com.hanghae.hanghaebnb.comment.dto.RequestComment;
import com.hanghae.hanghaebnb.comment.entity.Comment;
import com.hanghae.hanghaebnb.room.entity.Room;

import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toComment(String users, RequestComment requestComment, Room room){
        return Comment.builder()
                .witer(users)
                .contents(requestComment.getContents())
                .createdAt(requestComment.getCreatedAt())
                .modifiedAt(requestComment.getModifiedAt())
                .room(room)
                .build();
    }

}
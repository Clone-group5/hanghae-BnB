package com.hanghae.hanghaebnb.comment.dto;

import com.hanghae.hanghaebnb.comment.entity.Comment;
import com.hanghae.hanghaebnb.room.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class ResponseComment {

    private Long commentId;
    private String writer;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public ResponseComment(Long commentId, String writer, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.commentId = commentId;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

package com.hanghae.hanghaebnb.comment.entity;

import com.hanghae.hanghaebnb.room.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommentId;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Builder
    public Comment(String writer, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, Room room){
        this.writer = writer;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.room = room;
    }
}

package com.hanghae.hanghaebnb.room.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@NoArgsConstructor
@Getter
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long roomId;

    public Tag(Long roomId, String contents, long l){
        this.roomId = roomId;
        this.contents = contents;
    }
}

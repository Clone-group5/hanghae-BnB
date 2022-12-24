package com.hanghae.hanghaebnb.room.entity;

import lombok.Getter;

import javax.persistence.*;

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
}

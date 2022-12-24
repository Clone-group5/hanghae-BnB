package com.hanghae.hanghaebnb.book.entity;

import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.users.entity.Users;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private Long userId;

    private Long roomId;

    @Column(nullable = false)
    private String checkIn;

    @Column(nullable = false)
    private String checkOut;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Users users;
}

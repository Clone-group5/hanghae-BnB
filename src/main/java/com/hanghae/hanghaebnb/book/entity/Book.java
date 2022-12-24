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

    @Column(nullable = false)
    private String checkIn;

    @Column(nullable = false)
    private String checkOut;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private Users users;
}

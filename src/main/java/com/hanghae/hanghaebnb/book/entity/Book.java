package com.hanghae.hanghaebnb.book.entity;

import java.time.LocalDate;
import java.util.Date;

import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.users.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private Long headCount;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private Users users;
}

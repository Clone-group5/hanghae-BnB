package com.hanghae.hanghaebnb.users.entity;

import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.room.entity.Room;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private UserRoleEnum userRole;

    @OneToMany(mappedBy = "users")
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Book> books = new ArrayList<>();

}

package com.hanghae.hanghaebnb.users.entity;

import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.room.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = true)
    private Long kakaoId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UsersRoleEnum userRole;

    @OneToMany(mappedBy = "users")
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Book> books = new ArrayList<>();

    @Builder
    public Users(String email, String password, String nickname, UsersRoleEnum userRole, Long kakaoId) {
        this.email = email;
        this.kakaoId = kakaoId;
        this.password = password;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public Users kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}

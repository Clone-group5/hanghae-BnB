package com.hanghae.hanghaebnb.like.entity;

import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.users.entity.Users;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    private Long userId;

    private Long roomId;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Room rooms;
}

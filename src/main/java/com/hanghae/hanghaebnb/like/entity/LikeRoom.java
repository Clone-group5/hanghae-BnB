package com.hanghae.hanghaebnb.like.entity;

import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.users.entity.Users;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class LikeRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERS_ID")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private Room rooms;

}

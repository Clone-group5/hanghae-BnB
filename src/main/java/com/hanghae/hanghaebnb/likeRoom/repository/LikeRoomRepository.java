package com.hanghae.hanghaebnb.likeRoom.repository;

import com.hanghae.hanghaebnb.likeRoom.entity.LikeRoom;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRoomRepository extends JpaRepository<LikeRoom,Long> {

    Optional<LikeRoom> findLikeRoomByRoomsAndUsers(Room room, Users users);

}
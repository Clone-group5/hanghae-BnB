package com.hanghae.hanghaebnb.likeRoom.repository;

import com.hanghae.hanghaebnb.likeRoom.entity.LikeRoom;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRoomRepository extends JpaRepository<LikeRoom,Long> {

    Optional<LikeRoom> findLikeRoomByRoomsAndUsers(Room room, Users users);

    @Modifying
    @Query("DELETE FROM LikeRoom c WHERE c.rooms = :room")
    void deleteByRooms(@Param("room") Room room);

}

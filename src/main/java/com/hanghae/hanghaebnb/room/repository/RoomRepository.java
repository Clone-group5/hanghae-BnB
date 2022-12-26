package com.hanghae.hanghaebnb.room.repository;

import com.hanghae.hanghaebnb.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}

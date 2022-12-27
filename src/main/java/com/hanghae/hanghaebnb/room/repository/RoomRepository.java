package com.hanghae.hanghaebnb.room.repository;

import com.hanghae.hanghaebnb.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByLocation(String Location);
}

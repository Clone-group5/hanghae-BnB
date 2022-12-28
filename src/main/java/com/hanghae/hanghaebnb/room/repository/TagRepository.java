package com.hanghae.hanghaebnb.room.repository;

import com.hanghae.hanghaebnb.room.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByRoomId(Long roomId);

    @Modifying
    @Query("DELETE FROM Tag WHERE roomId = :roomId")
    void deleteByRoomId(@Param("roomId") Long roomId);
}

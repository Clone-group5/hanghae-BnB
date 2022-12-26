package com.hanghae.hanghaebnb.room.repository;

import com.hanghae.hanghaebnb.room.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByRoomId(Long roomId);
}

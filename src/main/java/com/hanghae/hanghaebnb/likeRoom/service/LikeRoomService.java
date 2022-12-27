package com.hanghae.hanghaebnb.likeRoom.service;

import com.hanghae.hanghaebnb.likeRoom.entity.LikeRoom;
import com.hanghae.hanghaebnb.likeRoom.repository.LikeRoomRepository;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import com.hanghae.hanghaebnb.users.UserRepository;
import com.hanghae.hanghaebnb.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static com.hanghae.hanghaebnb.common.exception.ErrorCode.NOT_FOUND_ROOM_EXCEPTION;
import static com.hanghae.hanghaebnb.common.exception.ErrorCode.NOT_FOUND_USERS_EXCEPTION;

@RequiredArgsConstructor
@Service
public class LikeRoomService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final LikeRoomRepository likeRoomRepository;

    @Transactional
    public boolean likeRoom(Long roomId) {
        boolean likeBoolean = false;

        Users users = userRepository.findById(1L).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_USERS_EXCEPTION.getMsg())
        );

        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_ROOM_EXCEPTION.getMsg())
        );

        Optional<LikeRoom> like = likeRoomRepository.findLikeRoomByRoomsAndUsers(room, users);

        if(like.isPresent()){
            LikeRoom likeRoom = like.get();
            room.unLike();
            likeRoomRepository.deleteById(likeRoom.getLikeId());
        }
        else{
            LikeRoom likeRoom = new LikeRoom(users, room);
            room.like();
            likeRoomRepository.save(likeRoom);
            likeBoolean = true;
        }
        return likeBoolean;
    }

}

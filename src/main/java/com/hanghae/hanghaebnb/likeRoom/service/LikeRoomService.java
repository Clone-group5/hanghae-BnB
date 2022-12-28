package com.hanghae.hanghaebnb.likeRoom.service;

import com.hanghae.hanghaebnb.likeRoom.dto.ResponseLikeRoom;
import com.hanghae.hanghaebnb.likeRoom.entity.LikeRoom;
import com.hanghae.hanghaebnb.likeRoom.repository.LikeRoomRepository;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import com.hanghae.hanghaebnb.users.entity.Users;
import com.hanghae.hanghaebnb.users.repository.UserRepository;
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
    public ResponseLikeRoom likeRoom(Long roomId, Users usersReceive) {

        Users users = userRepository.findById(usersReceive.getUserId()).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_USERS_EXCEPTION.getMsg())
        );

        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_ROOM_EXCEPTION.getMsg())
        );

        ResponseLikeRoom responseLikeRoom = new ResponseLikeRoom();
        Optional<LikeRoom> like = likeRoomRepository.findLikeRoomByRoomsAndUsers(room, users);

        if(like.isPresent()){
            LikeRoom likeRoom = like.get();
            room.unLike();
            likeRoomRepository.deleteById(likeRoom.getLikeId());
            responseLikeRoom.unlike();
        }
        else{
            LikeRoom likeRoom = new LikeRoom(users, room);
            room.like();
            likeRoomRepository.save(likeRoom);
            responseLikeRoom.like();
        }
        return responseLikeRoom;
    }

}

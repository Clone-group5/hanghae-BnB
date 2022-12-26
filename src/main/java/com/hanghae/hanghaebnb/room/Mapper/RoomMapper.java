package com.hanghae.hanghaebnb.room.Mapper;


import com.hanghae.hanghaebnb.room.dto.RoomRequestDto;
import com.hanghae.hanghaebnb.room.dto.RoomResponseDto;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.entity.Tag;
import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomMapper {
    public Room toRoom(RoomRequestDto roomRequestDto){
        return Room.builder()
                .title(roomRequestDto.getTitle())
                .contents(roomRequestDto.getContents())
                .price(roomRequestDto.getPrice())
                .extraPrice(roomRequestDto.getExtraPrice())
                .location(roomRequestDto.getLocation())
                .headDefault(roomRequestDto.getHeadDefault())
                .headMax(roomRequestDto.getHeadMax())
                .likeCount(0)
                .build();
    }

    public RoomResponseDto toRoomResponseDto(Room room, List<String> imgs, List<String> tags, Boolean like){
        return RoomResponseDto.builder()
                .roomId(room.getRoomId())
                .hostName(room.getUsers().getNickname())
                .title(room.getTitle())
                .contents(room.getContents())
                .location(room.getLocation())
                .headDefault(room.getHeadDefault())
                .headMax(room.getHeadMax())
                .price((room.getPrice()))
                .extraPrice(room.getExtraPrice())
                .likeCount(room.getLikeCount())
                .like(like)
                .imgs(imgs)
                .tags(tags)
                .build();
    }
}

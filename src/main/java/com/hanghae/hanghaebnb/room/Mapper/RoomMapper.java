package com.hanghae.hanghaebnb.room.Mapper;


import com.hanghae.hanghaebnb.room.dto.RoomRequestDto;
import com.hanghae.hanghaebnb.room.entity.Room;
import org.springframework.stereotype.Component;

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
}

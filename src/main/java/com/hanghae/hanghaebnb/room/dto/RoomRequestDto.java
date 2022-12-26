package com.hanghae.hanghaebnb.room.dto;

import com.hanghae.hanghaebnb.room.entity.Tag;
import com.hanghae.hanghaebnb.users.entity.Users;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
public class RoomRequestDto {

    private Long roomId;
    private String title;
    private String contents;
    private Long price;
    private Long extraPrice;
    private String location;
    private Integer headDefault;
    private Integer headMax;
    private Integer likeCount;
    private List<Tag> tags;

}

package com.hanghae.hanghaebnb.room.dto;

import java.util.List;

import com.hanghae.hanghaebnb.room.entity.Tag;

import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public RoomRequestDto(long roomId, String title, String contents, long price, long extraPrice, String location, int headDefault, int headMax, int likeCount, List<Tag> tags) {
        this.roomId = roomId;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.extraPrice = extraPrice;
        this.location = location;
        this.headDefault = headDefault;
        this.headMax = headMax;
        this.likeCount = likeCount;
        this.tags = tags;
    }
}

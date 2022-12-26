package com.hanghae.hanghaebnb.room.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class RoomListResponseDto {
    private Long roomId;
    private String hostName;
    private String title;
    private String contents;
    private Long price;
    private Long extraPrice;
    private String location;
    private Integer headDefault;
    private Integer headMax;
    private Integer likeCount;
    private List<String> imgs = new ArrayList<>();


    @Builder
    public RoomListResponseDto(Long roomId
            ,String hostName
            ,String title
            ,String contents
            ,String location
            ,Integer headDefault
            ,Integer headMax
            ,Long price
            ,Long extraPrice
            ,List<String> imgs
            ,Integer likeCount
    ){
        this.roomId = roomId;
        this.hostName = hostName;
        this.title = title;
        this.contents = contents;
        this.location = location;
        this.headDefault = headDefault;
        this.headMax = headMax;
        this.price = price;
        this.extraPrice = extraPrice;
        this.imgs = imgs;
        this.likeCount = likeCount;
    }
}

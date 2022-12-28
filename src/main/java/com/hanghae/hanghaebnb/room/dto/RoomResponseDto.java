package com.hanghae.hanghaebnb.room.dto;


import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.comment.dto.ResponseComment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class RoomResponseDto {
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
    private Boolean like;
    private List<String> tags = new ArrayList<>();
    private List<String> imgs = new ArrayList<>();
    private List<ResponseComment> comments = new ArrayList<>();
    //private List<Book> books = new ArrayList<>();
    @Builder
    public RoomResponseDto(Long roomId
                            ,String hostName
                            ,String title
                            ,String contents
                            ,String location
                            ,Integer headDefault
                            ,Integer headMax
                            ,Long price
                            ,Long extraPrice
                           ,List<String> imgs
                           ,List<String> tags
                            ,List<ResponseComment> comments
                           ,List<Book> books
                           ,Integer likeCount
                           ,Boolean like
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
        this.tags = tags;
        this.comments = comments;
        //this.books = books;
        this.likeCount = likeCount;
        this.like = like;
    }

}

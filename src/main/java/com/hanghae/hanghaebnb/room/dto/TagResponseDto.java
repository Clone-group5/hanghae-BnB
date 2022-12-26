package com.hanghae.hanghaebnb.room.dto;


import com.hanghae.hanghaebnb.room.entity.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TagResponseDto {
    private String contents;

    public TagResponseDto(Tag tag){
        this.contents = tag.getContents();
    }
}

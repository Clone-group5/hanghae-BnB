package com.hanghae.hanghaebnb.room.Mapper;


import com.hanghae.hanghaebnb.room.dto.TagResponseDto;
import com.hanghae.hanghaebnb.room.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagResponseDto toTagResponseDto(Tag tag){
        return new TagResponseDto(tag);
    }

}

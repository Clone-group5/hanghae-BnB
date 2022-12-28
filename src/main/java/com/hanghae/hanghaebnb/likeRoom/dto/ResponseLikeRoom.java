package com.hanghae.hanghaebnb.likeRoom.dto;

import lombok.Getter;

@Getter
public class ResponseLikeRoom {

    private Boolean like = false;

    public void like(){
        this.like = true;
    }
    public void unlike(){
        this.like = false;
    }

}

package com.hanghae.hanghaebnb.book.dto;

import com.hanghae.hanghaebnb.book.entity.Book;
import lombok.Getter;

@Getter
public class ResponseBook {
    private Long roomId;

    private String title;

    private String checkIn;

    private String checkOut;

    private Long headCount;

    private Long totalPrice;


    public ResponseBook(Book book){
        this.roomId = book.getRoom().getRoomId();
        this.title = book.getRoom().getTitle();
        this.checkIn = book.getCheckIn();
        this.checkOut = book.getCheckOut();
        this.headCount = book.getHeadCount();
        this.totalPrice = book.getTotalPrice();
    }
}

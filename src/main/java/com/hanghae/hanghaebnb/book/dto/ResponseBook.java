package com.hanghae.hanghaebnb.book.dto;

import com.hanghae.hanghaebnb.book.entity.Book;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

import java.util.List;

@Getter
public class ResponseBook {
    private Long roomId;

    private Long bookId;

    private String title;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long headCount;

    private Long totalPrice;

    private List<String> photos;


    public ResponseBook(Book book, List<String> photos){
        this.roomId = book.getRoom().getRoomId();
        this.bookId = book.getBookId();
        this.title = book.getRoom().getTitle();
        this.checkIn = book.getCheckIn();
        this.checkOut = book.getCheckOut();
        this.headCount = book.getHeadCount();
        this.totalPrice = book.getTotalPrice();
        this.photos = photos;
    }
}

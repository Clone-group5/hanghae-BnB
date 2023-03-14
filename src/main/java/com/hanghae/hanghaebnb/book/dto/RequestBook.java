package com.hanghae.hanghaebnb.book.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestBook {
    private Long roomId;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long headCount;

    private Long totalPrice;

    public RequestBook(Long roomId, LocalDate checkIn, LocalDate checkOut, Long headCount, Long totalPrice){
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.headCount = headCount;
        this.totalPrice = totalPrice;
    }

}

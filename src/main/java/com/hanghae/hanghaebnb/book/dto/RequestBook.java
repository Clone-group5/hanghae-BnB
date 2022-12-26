package com.hanghae.hanghaebnb.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestBook {
    private Long roomId;

    private String checkIn;

    private String checkOut;

    private Long headCount;
}

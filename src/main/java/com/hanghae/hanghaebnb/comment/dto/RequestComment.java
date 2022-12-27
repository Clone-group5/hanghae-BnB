package com.hanghae.hanghaebnb.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class RequestComment {

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}

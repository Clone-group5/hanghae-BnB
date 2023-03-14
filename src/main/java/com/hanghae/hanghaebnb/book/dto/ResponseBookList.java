package com.hanghae.hanghaebnb.book.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseBookList {

    private List<ResponseBook> responseBookList = new ArrayList<>();

    public void addBook(ResponseBook responseBook){
        responseBookList.add(responseBook);
    }

}


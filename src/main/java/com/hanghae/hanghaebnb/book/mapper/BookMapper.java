package com.hanghae.hanghaebnb.book.mapper;

import com.hanghae.hanghaebnb.book.dto.RequestBook;
import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.users.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toBook(Room room, RequestBook requestBook, Long total, Users users){
        return Book.builder()
                .headCount(requestBook.getHeadCount())
                .totalPrice(total)       //추가해야함
                .checkIn(requestBook.getCheckIn())
                .checkOut(requestBook.getCheckOut())
                .room(room)
                .users(users)        //추가 예정
                .build();
    }

}

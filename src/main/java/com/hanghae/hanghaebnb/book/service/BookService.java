package com.hanghae.hanghaebnb.book.service;

import com.hanghae.hanghaebnb.book.dto.RequestBook;
import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.book.mapper.BookMapper;
import com.hanghae.hanghaebnb.book.repository.BookRepository;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.hanghae.hanghaebnb.common.exception.ErrorCode.NOT_FOUND_ROOM_EXCEPTION;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final RoomRepository roomRepository;
    private final BookMapper bookMapper;

    //예약 등록
    @Transactional
    public void addBook(Long roomId, RequestBook requestBook) {
        //유저 확인 추가 예정

        //room확인
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_ROOM_EXCEPTION.getMsg())
        );

        //totalprice 계산
        Long total = 1234L;     //추가예정

        Book book = bookMapper.toBook(room, requestBook, total);
        room.addBook(book);
        bookRepository.save(book);
    }
}

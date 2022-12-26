package com.hanghae.hanghaebnb.book.service;

import com.hanghae.hanghaebnb.book.dto.RequestBook;
import com.hanghae.hanghaebnb.book.dto.ResponseBook;
import com.hanghae.hanghaebnb.book.dto.ResponseBookList;
import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.book.mapper.BookMapper;
import com.hanghae.hanghaebnb.book.repository.BookRepository;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import com.hanghae.hanghaebnb.users.entity.Users;
import com.hanghae.hanghaebnb.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static com.hanghae.hanghaebnb.common.exception.ErrorCode.NOT_FOUND_ROOM_EXCEPTION;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final RoomRepository roomRepository;
    private final UsersRepository usersRepository;
    private final BookMapper bookMapper;

    /*
     * 예약 조회
     */
    @Transactional
    public ResponseBookList showBook() {
        Users users = usersRepository.findById(Long.valueOf(1)).orElseThrow(
                () -> new IllegalArgumentException("없는 유저입니다.")
        );/* 임시로 넣어둔 것 시큐리티 구현 후 수정 */

        ResponseBookList responseBookList = new ResponseBookList();
        List<Book> books = bookRepository.findAllByUsers(users);
        for (Book book : books) {
            responseBookList.addBook(new ResponseBook(book));
        }
        return responseBookList;
    }

    /*
     * 예약 등록
     */
    @Transactional
    public void addBook(Long roomId, RequestBook requestBook) {
        /* 임시로 넣어둔 것 시큐리티 구현 후 수정 */
        Users users = usersRepository.findById(Long.valueOf(1)).orElseThrow(
                () -> new IllegalArgumentException("없는 유저입니다.")
        );

        //room확인
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_ROOM_EXCEPTION.getMsg())
        );

        //totalprice 계산
        Long total = 1234L;     //추가예정

        Book book = bookMapper.toBook(room, requestBook, total, users);
        room.addBook(book);
        bookRepository.save(book);
    }

    /*
     * 예약 취소
     */
    @Transactional
    public void deleteBook(Long bookId) {
        //예약 확인
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_ROOM_EXCEPTION.getMsg())
        );
        //유저 확인 추가 예정

        //예약 취소
        bookRepository.deleteById(bookId);
    }

}

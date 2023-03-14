package com.hanghae.hanghaebnb.book.service;

import com.hanghae.hanghaebnb.book.dto.RequestBook;
import com.hanghae.hanghaebnb.book.dto.ResponseBook;
import com.hanghae.hanghaebnb.book.dto.ResponseBookList;
import com.hanghae.hanghaebnb.book.entity.Book;
import com.hanghae.hanghaebnb.book.mapper.BookMapper;
import com.hanghae.hanghaebnb.book.repository.BookRepository;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import com.hanghae.hanghaebnb.room.service.RoomService;
import com.hanghae.hanghaebnb.users.entity.Users;
import com.hanghae.hanghaebnb.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import static com.hanghae.hanghaebnb.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomService roomService;
    private final BookMapper bookMapper;

    /*
     * 예약 조회
     */
    @Transactional
    public ResponseBookList showBook(Users usersReceive) {
        Users users = userRepository.findById(usersReceive.getUserId()).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_USERS_EXCEPTION.getMsg())
        );

        ResponseBookList responseBookList = new ResponseBookList();
        List<Book> books = bookRepository.findAllByUsers(users);
        for (Book book : books) {
            responseBookList.addBook(new ResponseBook(book, roomService.getPhotoName(book.getRoom().getRoomId())));
        }

        return responseBookList;
    }

    @Transactional
    public void addBook(Long roomId, RequestBook requestBook, Users usersReceive) throws ParseException {

        Users users = userRepository.findById(usersReceive.getUserId()).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_USERS_EXCEPTION.getMsg())
        );
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_ROOM_EXCEPTION.getMsg())
        );

        Long duplicationCheckIn = bookRepository.findByCheckIn(requestBook.getCheckIn(), requestBook.getCheckOut());
        Long duplicationCheckOut = bookRepository.findByCheckOut(requestBook.getCheckIn(), requestBook.getCheckOut());

        if(duplicationCheckIn == roomId || duplicationCheckOut == roomId){
            throw new IllegalArgumentException(BOOK_FAIL.getMsg());
        }

        Book book = bookMapper.toBook(room, requestBook, requestBook.getTotalPrice(), users);
        room.addBook(book);
        bookRepository.save(book);

    }

    @Transactional
    public void deleteBook(Long bookId, Users usersReceive) {

        Users users = userRepository.findById(usersReceive.getUserId()).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_USERS_EXCEPTION.getMsg())
        );
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException(NOT_FOUND_ROOM_EXCEPTION.getMsg())
        );

        bookRepository.deleteById(bookId);
    }

}

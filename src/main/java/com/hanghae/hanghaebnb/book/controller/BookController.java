package com.hanghae.hanghaebnb.book.controller;

import com.hanghae.hanghaebnb.book.dto.RequestBook;
import com.hanghae.hanghaebnb.book.dto.ResponseBookList;
import com.hanghae.hanghaebnb.book.service.BookService;
import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import com.hanghae.hanghaebnb.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    /*
     * 예약 조회
     */
    @GetMapping("/book")
    public ResponseEntity<ResponseDto> showBook(@AuthenticationPrincipal UserDetailsImpl userDetails){
        ResponseBookList responseBookList = bookService.showBook(userDetails.getUsers());
        ResponseDto responseDto = new ResponseDto(200, "예약 정보 조회가 완료되었습니다.", responseBookList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /*
     * 예약 등록
     */
    @PostMapping("/room/{roomId}/book")
    public ResponseEntity<ResponseDto> addBook(@PathVariable Long roomId, @RequestBody RequestBook requestBook, @AuthenticationPrincipal UserDetailsImpl userDetails) throws ParseException {
        bookService.addBook(roomId, requestBook, userDetails.getUsers());
        ResponseDto responseDto = new ResponseDto(200, "예약이 완료되었습니다.", null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /*
     * 예약 취소
     */
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable Long bookId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        bookService.deleteBook(bookId, userDetails.getUsers());
        ResponseDto responseDto = new ResponseDto(200, "숙소 예약 취소가 완료되었습니다.", null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

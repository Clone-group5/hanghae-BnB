package com.hanghae.hanghaebnb.book.controller;

import com.hanghae.hanghaebnb.book.dto.RequestBook;
import com.hanghae.hanghaebnb.book.dto.ResponseBookList;
import com.hanghae.hanghaebnb.book.service.BookService;
import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    /*
     * 예약 조회
     */
    @GetMapping()
    public ResponseEntity<ResponseDto> showBook(){
        ResponseBookList responseBookList = bookService.showBook();
        ResponseDto responseDto = new ResponseDto(200, "예약 정보 조회가 완료되었습니다.", responseBookList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /*
     * 예약 등록
     */
    @PostMapping("/{roomId}")
    public ResponseEntity<ResponseDto> addBook(@PathVariable Long roomId, @RequestBody RequestBook requestBook){
        bookService.addBook(roomId, requestBook);
        ResponseDto responseDto = new ResponseDto(200, "예약이 완료되었습니다.", null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /*
     * 예약 취소
     */
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable Long bookId){
        bookService.deleteBook(bookId);
        ResponseDto responseDto = new ResponseDto(200, "숙소 예약 취소가 완료되었습니다.", null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

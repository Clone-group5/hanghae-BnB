package com.hanghae.hanghaebnb.likeRoom.controller;

import com.hanghae.hanghaebnb.book.service.BookService;
import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import com.hanghae.hanghaebnb.common.security.UserDetailsImpl;
import com.hanghae.hanghaebnb.likeRoom.service.LikeRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeRoomController {

    private final LikeRoomService likeRoomService;

    @PostMapping("/room/{roomId}/like")
    public ResponseEntity<ResponseDto> likeRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        boolean like = likeRoomService.likeRoom(roomId, userDetails.getUsers());
        if(like){
            ResponseDto responseDto = new ResponseDto<>(200, "숙소 좋아요가 완료되었습니다.", like);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        else{
            ResponseDto responseDto = new ResponseDto<>(200, "숙소 좋아요가 취소되었습니다.", like);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

}

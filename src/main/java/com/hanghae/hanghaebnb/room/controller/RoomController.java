package com.hanghae.hanghaebnb.room.controller;


import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import com.hanghae.hanghaebnb.room.dto.RoomResponseDto;
import com.hanghae.hanghaebnb.room.service.PhotoService;
import com.hanghae.hanghaebnb.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomController {

    private final RoomService roomService;
    private final PhotoService photoService;


    @PostMapping("/room")
    public ResponseEntity postRoom(HttpServletRequest httpServletRequest
                        ,@RequestParam("MultipartFile") MultipartFile[] multipartFiles)
            throws Exception {
        Long roomId = roomService.postRoom(httpServletRequest.getParameter("room"), multipartFiles);
        return new ResponseEntity(new ResponseDto(200, "숙소 정보 등록이 완료되었습니다.",null), HttpStatus.OK);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity getRoom(@PathVariable Long roomId) throws IOException {
        RoomResponseDto roomResponseDto = roomService.getRoom(roomId);
        return new ResponseEntity(new ResponseDto(200, "숙소 정보 조회가 완료되었습니다.",roomResponseDto), HttpStatus.OK);
    }

}

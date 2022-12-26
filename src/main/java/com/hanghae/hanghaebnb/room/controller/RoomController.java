package com.hanghae.hanghaebnb.room.controller;


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
public class RoomController {

    private final RoomService roomService;
    private final PhotoService photoService;


    @PostMapping("/room")
    public void postRoom(HttpServletRequest httpServletRequest
                        ,@RequestParam("MultipartFile") MultipartFile[] multipartFiles)
            throws Exception {
        Long roomId = roomService.postRoom(httpServletRequest.getParameter("room"), multipartFiles);
    }

//    @GetMapping("/room/{roomId}")
//    public ResponseEntity<List<byte[]>> getRoom(@PathVariable Long roomId) throws IOException {
//
//        //return roomService.photoDownload(roomId);
//    }
}

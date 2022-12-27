package com.hanghae.hanghaebnb.room.controller;


import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import com.hanghae.hanghaebnb.common.security.UserDetailsImpl;
import com.hanghae.hanghaebnb.room.dto.RoomListResponseDto;
import com.hanghae.hanghaebnb.room.dto.RoomResponseDto;
import com.hanghae.hanghaebnb.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/room")
    public ResponseEntity postRoom(HttpServletRequest httpServletRequest
                                    ,@RequestParam(value = "tags") String[] tags
            , @RequestParam(value ="MultipartFile", required=false) MultipartFile[] multipartFiles
            , @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws Exception {
        Long roomId = roomService.postRoom(httpServletRequest, tags, multipartFiles, userDetails.getUsers());
        return new ResponseEntity(new ResponseDto(200, "숙소 정보 등록이 완료되었습니다.",null), HttpStatus.OK);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity getRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        RoomResponseDto roomResponseDto = roomService.getRoom(roomId, userDetails.getUsers());
        return new ResponseEntity(new ResponseDto(200, "숙소 정보 조회가 완료되었습니다.",roomResponseDto), HttpStatus.OK);
    }

    @GetMapping("/main")
    public ResponseEntity getRooms(@RequestBody(required = false) Map<String, String> category){

        List<RoomListResponseDto> roomList;
        if(category == null || category.get("category").equals("전체")){
            System.out.println("category 전체 조회");
            roomList = roomService.getRooms();
        }else{
            System.out.println("category 별 조회 ==>> " + category.get("category"));
            roomList = roomService.getRoomsByCategory(category.get("category"));
        }
        return new ResponseEntity(new ResponseDto(200, "조회가 완료되었습니다.", roomList), HttpStatus.OK);
    }


    @DeleteMapping("/room/{roomId}")
    public ResponseEntity deleteRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        roomService.deleteRoom(roomId, userDetails.getUsers());
        return new ResponseEntity(new ResponseDto(200, "삭제가 완료되었습니다.", null), HttpStatus.OK);
    }
}

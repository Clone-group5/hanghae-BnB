package com.hanghae.hanghaebnb.comment.controller;

import com.hanghae.hanghaebnb.comment.dto.RequestComment;
import com.hanghae.hanghaebnb.comment.service.CommentService;
import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room/{roomid}")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/comment")
    public ResponseDto createComment(@PathVariable Long roomid,
                                     HttpServletRequest request, RequestComment requestComment){
        commentService.createComment(roomid,requestComment, request);

        return new ResponseDto(200,"후기 등록이 완료되었습니다.",null);
    }


}

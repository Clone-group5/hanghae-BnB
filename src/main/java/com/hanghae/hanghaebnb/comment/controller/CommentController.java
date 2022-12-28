package com.hanghae.hanghaebnb.comment.controller;

import com.hanghae.hanghaebnb.comment.dto.RequestComment;
import com.hanghae.hanghaebnb.comment.service.CommentService;
import com.hanghae.hanghaebnb.common.dto.ResponseDto;
import com.hanghae.hanghaebnb.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/{roomid}/comment")
    public ResponseDto createComment(@PathVariable Long roomid,@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @RequestBody RequestComment requestComment){

        commentService.createComment(roomid,requestComment, userDetails.getUsers());
        return new ResponseDto(200,"후기 등록이 완료되었습니다.",null);

    }
    @PostMapping("/comment/{commentid}")
    public ResponseDto updateComment(@PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @RequestBody RequestComment requestComment){

        commentService.updateComment(commentid,requestComment,userDetails.getUsers());
        return new ResponseDto(200, "후기 수정이 완료되었습니다.",null);
    }


}

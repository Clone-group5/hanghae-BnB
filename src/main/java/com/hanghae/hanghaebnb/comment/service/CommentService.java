package com.hanghae.hanghaebnb.comment.service;


import com.hanghae.hanghaebnb.comment.dto.RequestComment;
import com.hanghae.hanghaebnb.comment.entity.Comment;
import com.hanghae.hanghaebnb.comment.mapper.CommentMapper;
import com.hanghae.hanghaebnb.comment.repository.CommentRepository;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final RoomRepository roomRepository;

    //    private final UsersRepository usersRepository;
    private final CommentMapper mapper;

    public void createComment(Long roomid, RequestComment requestComment, HttpServletRequest request) {

        Room room = roomRepository.findById(roomid).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 숙소입니다.")
        );
        Comment comment = mapper.toComment("임시 작성자",requestComment, room);

        commentRepository.save(comment);

    }
}

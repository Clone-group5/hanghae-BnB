package com.hanghae.hanghaebnb.comment.service;


import com.hanghae.hanghaebnb.comment.dto.RequestComment;
import com.hanghae.hanghaebnb.comment.entity.Comment;
import com.hanghae.hanghaebnb.comment.mapper.CommentMapper;
import com.hanghae.hanghaebnb.comment.repository.CommentRepository;
import com.hanghae.hanghaebnb.common.exception.CustomException;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import com.hanghae.hanghaebnb.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hanghae.hanghaebnb.common.exception.ErrorCode.NOT_FOUND_ROOM_EXCEPTION;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final RoomRepository roomRepository;
    private final CommentMapper mapper;

    public void createComment(Long roomid, RequestComment requestComment, Users users) {

        Room room = roomRepository.findById(roomid).orElseThrow(
                () -> new CustomException(NOT_FOUND_ROOM_EXCEPTION)
        );

        Comment comment = mapper.toComment(users,requestComment, room);

        commentRepository.save(comment);

    }
}

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
import org.springframework.transaction.annotation.Transactional;

import static com.hanghae.hanghaebnb.common.exception.ErrorCode.*;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final RoomRepository roomRepository;
    private final CommentMapper mapper;

    @Transactional
    public void createComment(Long roomid, RequestComment requestComment, Users users) {

        Room room = roomRepository.findById(roomid).orElseThrow(
                () -> new CustomException(NOT_FOUND_ROOM_EXCEPTION)
        );

        Comment comment = mapper.toComment(users,requestComment, room);

        commentRepository.save(comment);

    }

    public void updateComment(Long commentid, RequestComment requestComment, Users users) {


        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT_EXCEPTION)
        );

        if(!comment.getUsers().getUserId().equals(users.getUserId())){
            throw new CustomException(NOT_MATCH_USER_INFO);
        }

        comment.update(requestComment.getContents());

        commentRepository.save(comment);

    }

    public void deleteComment(Long commentid, Users users) {

        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT_EXCEPTION)
        );
        if(!comment.getUsers().getUserId().equals(users.getUserId())){
            throw new CustomException(NOT_MATCH_USER_INFO);
        }

        commentRepository.deleteById(commentid);

    }
}

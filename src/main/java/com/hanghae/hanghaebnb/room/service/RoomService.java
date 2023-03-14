package com.hanghae.hanghaebnb.room.service;


import com.amazonaws.services.s3.AmazonS3Client;


import com.amazonaws.services.s3.model.*;
import com.hanghae.hanghaebnb.comment.dto.ResponseComment;
import com.hanghae.hanghaebnb.comment.entity.Comment;
import com.hanghae.hanghaebnb.comment.mapper.CommentMapper;
import com.hanghae.hanghaebnb.comment.repository.CommentRepository;
import com.hanghae.hanghaebnb.common.exception.CustomException;
import com.hanghae.hanghaebnb.common.exception.ErrorCode;
import com.hanghae.hanghaebnb.likeRoom.entity.LikeRoom;
import com.hanghae.hanghaebnb.likeRoom.repository.LikeRoomRepository;
import com.hanghae.hanghaebnb.room.Mapper.RoomMapper;
import com.hanghae.hanghaebnb.room.dto.RoomListResponseDto;
import com.hanghae.hanghaebnb.room.dto.RoomResponseDto;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.entity.Tag;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import com.hanghae.hanghaebnb.room.repository.TagRepository;
import com.hanghae.hanghaebnb.users.entity.Users;
import com.hanghae.hanghaebnb.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoomService {


    private String bucket = "hanghae-bnb";//버켓 이름
    private final AmazonS3Client amazonS3Client;
    private final RoomRepository roomRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final LikeRoomRepository likeRoomRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public Long postRoom(HttpServletRequest httpServletRequest, String[] tags, MultipartFile[] multipartFiles, Users users) throws IOException {

        Room room = new Room(
                httpServletRequest.getParameter("title")
                , httpServletRequest.getParameter("contents")
                , Long.parseLong(httpServletRequest.getParameter("price"))
                , Long.parseLong(httpServletRequest.getParameter("extraPrice"))
                , httpServletRequest.getParameter("location")
                , Integer.parseInt(httpServletRequest.getParameter("headDefault"))
                , Integer.parseInt(httpServletRequest.getParameter("headMax"))
                ,""
                ,0
                ,users
        );

        roomRepository.save(room);
        String folderPath = photoUpload(multipartFiles, room.getRoomId());
        room.imgUpdate(folderPath);

        for(String tag : tags){
            tagRepository.save(new Tag(room.getRoomId(), tag, 1L));
        }


        return room.getRoomId();
    }

    @Transactional(readOnly = true)
    public RoomResponseDto getRoom(Long roomId, Long userId) {

        List<String> tagList = new ArrayList<>();

        Room room = roomRepository.findById(roomId).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND_ROOM_EXCEPTION)
        );

        Boolean likeByLoginUser = false;

        if(userId != -1){

            Users users = userRepository.findById(userId).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_USERS_EXCEPTION)
            );

            Optional<LikeRoom> like = likeRoomRepository.findLikeRoomByRoomsAndUsers(room, users);

            if (like.isPresent()) {
                likeByLoginUser = true;
            }
        }

        List<Tag> tags = tagRepository.findAllByRoomId(roomId);
        for (Tag tag:tags) {
            tagList.add(tag.getContents());
        }

        List<String> imgs = getPhotoName(roomId);


        CommentMapper commentMapper = new CommentMapper();

        List<ResponseComment> responseComments = new ArrayList<>();
        for (Comment comment:room.getComments()) {
            responseComments.add(commentMapper.toResponseComment(comment));
        }

        RoomMapper roomMapper = new RoomMapper();
        RoomResponseDto roomResponseDto = roomMapper.toRoomResponseDto(room,  imgs, responseComments, tagList,  likeByLoginUser);
        return roomResponseDto;
    }

    @Transactional(readOnly = true)
    public List<RoomListResponseDto> getRooms() {
        List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "roomId"));
        List<RoomListResponseDto> roomResponseList= new ArrayList<>();
        RoomMapper roomMapper = new RoomMapper();

        for (Room room:roomList) {
            List<String> imgs = getPhotoName(room.getRoomId());
            roomResponseList.add(roomMapper.toRoomListResponseDto(room,imgs));
        }
        return roomResponseList;
    }

    @Transactional(readOnly = true)
    public List<RoomListResponseDto> getRoomsByCategory(String category) {

        List<Room> roomList = roomRepository.findAllByLocationOrderByRoomIdDesc(category);

        List<RoomListResponseDto> roomResponseList= new ArrayList<>();

        RoomMapper roomMapper = new RoomMapper();

        for (Room room:roomList) {
            List<String> imgs = getPhotoName(room.getRoomId());
            roomResponseList.add(roomMapper.toRoomListResponseDto(room,imgs));
        }

        return roomResponseList;
    }

    @Transactional
    public void deleteRoom(Long roomId, Users users) {

        Room room = roomRepository.findById(roomId).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND_ROOM_EXCEPTION)
        );
        if(room.getUsers().getUserId() != users.getUserId()){
            throw new CustomException(ErrorCode.AUTHORIZATION_FAIL);
        }
        commentRepository.deleteByRoom(room);
        likeRoomRepository.deleteByRooms(room);
        tagRepository.deleteByRoomId(room.getRoomId());
        roomRepository.deleteById(roomId);
        photoDelete(roomId);
    }

    //아마존 S3 사진 업로드
    public String photoUpload(MultipartFile[] multipartFiles, Long roomId) throws IOException {
        //List<String> imagePathList = new ArrayList<>();
        String folderPath = "";
        for(MultipartFile file : multipartFiles){
            String originalName = roomId + "/" + file.getOriginalFilename();
            long size = file.getSize();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(size);

            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, originalName, file.getInputStream(), objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            folderPath = amazonS3Client.getUrl(bucket, roomId.toString()).toString();
        }
        return folderPath;
    }

    public List<String> getPhotoName(Long roomId){

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                                                        .withBucketName(bucket)
                                                        .withPrefix(roomId+"/");
        ObjectListing objectListing  = amazonS3Client.listObjects(listObjectsRequest);
        List<String> photos = new ArrayList<>();

        for (S3ObjectSummary summary:objectListing.getObjectSummaries()) {
            photos.add(amazonS3Client.getUrl(bucket, summary.getKey()).toString());
       }

       return photos;

    }


    public void photoDelete(Long roomId){

        ObjectListing objectList = amazonS3Client.listObjects( bucket, roomId+"/" );
        List<S3ObjectSummary> objectSummeryList =  objectList.getObjectSummaries();
        String[] keysList = new String[ objectSummeryList.size() ];
        int count = 0;
        for( S3ObjectSummary summery : objectSummeryList ) {
            keysList[count++] = summery.getKey();
        }
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest( bucket ).withKeys( keysList );
        amazonS3Client.deleteObjects(deleteObjectsRequest);
    }



}

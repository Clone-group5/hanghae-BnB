package com.hanghae.hanghaebnb.room.service;


import com.amazonaws.services.s3.AmazonS3Client;


import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.hanghaebnb.common.exception.CustomException;
import com.hanghae.hanghaebnb.common.exception.ErrorCode;
import com.hanghae.hanghaebnb.room.Mapper.RoomMapper;
import com.hanghae.hanghaebnb.room.Mapper.TagMapper;
import com.hanghae.hanghaebnb.room.dto.RoomListResponseDto;
import com.hanghae.hanghaebnb.room.dto.RoomResponseDto;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.entity.Tag;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import com.hanghae.hanghaebnb.room.repository.TagRepository;
import com.hanghae.hanghaebnb.users.entity.Users;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoomService {


    private String bucket = "hanghae-bnb";//버켓 이름
    private final AmazonS3Client amazonS3Client;
    private final RoomRepository roomRepository;
    private final TagRepository tagRepository;
    //room 올리기
    @Transactional
    public Long postRoom(String jsonRoom,MultipartFile[] multipartFiles) throws JsonProcessingException,IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonRoom);
        Room room = new Room(
                jsonNode.get("title").asText()
                ,jsonNode.get("contents").asText()
                ,jsonNode.get("price").asLong()
                ,jsonNode.get("extraPrice").asLong()
                ,jsonNode.get("location").asText()
                ,jsonNode.get("headDefault").asInt()
                ,jsonNode.get("headMax").asInt()
                ,""
                ,0
                //,user
        );

        roomRepository.save(room);
        String folderPath = photoUpload(multipartFiles, room.getRoomId());
        room.imgUpdate(folderPath);
        Iterator<JsonNode> tags = jsonNode.get("tags").elements();

        while (tags.hasNext()) {
            Tag tag = new Tag(room.getRoomId(), tags.next().asText() );
            tagRepository.save(tag);
        }

        return room.getRoomId();
    }



    public RoomResponseDto getRoom(Long roomId) {
        List<String> tagList = new ArrayList<>();
        Room room = roomRepository.findById(roomId).orElseThrow(
                ()->new CustomException(ErrorCode.NOT_FOUND_ROOM_EXCEPTION)
        );
        List<Tag> tags = tagRepository.findAllByRoomId(roomId);
        for (Tag tag:tags) {
            tagList.add(tag.getContents());
        }
        List<String> imgs = getPhotoName(roomId);
        RoomMapper roomMapper = new RoomMapper();
        RoomResponseDto roomResponseDto = roomMapper.toRoomResponseDto(room,  imgs,tagList, true/*추후 보완*/);
        return roomResponseDto;
    }

    public List<RoomListResponseDto> getRooms() {
        List<Room> roomList = roomRepository.findAll();
        List<RoomListResponseDto> roomResponseList= new ArrayList<>();
        RoomMapper roomMapper = new RoomMapper();

        for (Room room:roomList) {
            List<String> imgs = getPhotoName(room.getRoomId());
            roomResponseList.add(roomMapper.toRoomListResponseDto(room,imgs));
        }
        return roomResponseList;

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


}

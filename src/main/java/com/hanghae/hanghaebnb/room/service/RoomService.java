package com.hanghae.hanghaebnb.room.service;


import com.amazonaws.services.s3.AmazonS3Client;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.hanghaebnb.room.entity.Room;
import com.hanghae.hanghaebnb.room.entity.Tag;
import com.hanghae.hanghaebnb.room.repository.RoomRepository;
import com.hanghae.hanghaebnb.room.repository.TagRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Iterator;



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

    //아마존 S3 사진 업로드
    public String photoUpload(MultipartFile[] multipartFiles, Long roomId) throws IOException {
        //List<String> imagePathList = new ArrayList<>();
        String folderPath = "";
        for(MultipartFile file : multipartFiles){
            String originalName = roomId + "/" + file.getOriginalFilename();
            System.out.println("originalName >>>>> " +originalName);
            long size = file.getSize();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(size);

            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, originalName, file.getInputStream(), objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            //String imagePath = amazonS3Client.getUrl(bucket, originalName).toString();
            folderPath = amazonS3Client.getUrl(bucket, roomId.toString()).toString();
            System.out.println("image path :::::::: 파일업로드 :::::::::: " + folderPath);
            //imagePathList.add(imagePath);

        }
        return folderPath;
    }


    //사진 다운로드 비활성화 필요없음
//    public ResponseEntity<List<byte[]>> photoDownload(Long roomId) throws IOException {
//
//        String folderPath = "";
//        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
//                                                        .withBucketName(bucket)
//                                                        .withPrefix(roomId+"/");
//        ObjectListing objectListing  = amazonS3Client.listObjects(listObjectsRequest);
//        List<byte[]> photos = new ArrayList<>();
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        for (S3ObjectSummary summary:objectListing.getObjectSummaries()) {
//            System.out.println(summary.getKey());
//            S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucket, summary.getKey()));
//            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
//            ZipEntry zipEntry = new ZipEntry(summary.getKey());
//
//            byte[] bytes = IOUtils.toByteArray(s3ObjectInputStream);
//
//            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//            BufferedImage image = ImageIO.read(bais);
//
//            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            httpHeaders.setContentLength(bytes.length);
//            httpHeaders.setContentDispositionFormData("attachment", summary.getKey());
//            System.out.println("middle check @@@@@@@@@@@@@@@@@@@@");
//            photos.add(bytes);
//        }
//
//        return new ResponseEntity<byte[]>(photos, httpHeaders, HttpStatus.OK);
//
//    }
}

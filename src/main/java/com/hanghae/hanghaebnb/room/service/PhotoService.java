package com.hanghae.hanghaebnb.room.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PhotoService {

    private String bucket = "hanghae-bnb";//버켓 이름
    private final AmazonS3Client amazonS3Client;

    public List<String> photoUpload(MultipartFile[] multipartFiles
                                    ,Long roomId) throws Exception{
        List<String> imagePathList = new ArrayList<>();

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

            String imagePath = amazonS3Client.getUrl(bucket, originalName).toString();
            System.out.println("image path :::::::: 파일업로드 :::::::::: " + imagePath);
            imagePathList.add(imagePath);

        }
        return imagePathList;
    }
}

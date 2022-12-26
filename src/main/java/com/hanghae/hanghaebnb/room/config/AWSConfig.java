package com.hanghae.hanghaebnb.room.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
    @Value("${amazon.s3.iamAccess}")
    private String iamAccess;
    @Value("${amazon.s3.iamSecretKey}")
    private String iamSecretKey;
    @Value("${amazon.s3.region}")
    private String region;

    @Bean
    public AmazonS3Client amazonS3Client(){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(iamAccess, iamSecretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}

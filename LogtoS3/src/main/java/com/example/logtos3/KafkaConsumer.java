package com.example.logtos3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@Slf4j
public class KafkaConsumer {
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${logging.file.path}")
    private String logFilePath;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @KafkaListener(topics="log-topic",groupId="itstudy")
    public void listen(String message) throws Exception {
        log.info(message);

        try {
            // 메시지를 로그 파일에 추가
            File logFile = new File(logFilePath);
            try (FileWriter writer = new FileWriter(logFile, true)) {
                writer.write(message + System.lineSeparator());
            }

            // 로그 파일을 S3에 업로드
            uploadFileToS3(logFile);

        } catch (Exception e) {
            log.error("Error processing Kafka message", e);
        }

    }

    private void uploadFileToS3(File file) {
        try {
            String s3FileName = CommonUtils.fileNameCreate(file.getName()); // 파일명 생성

            // ContentType 설정
            Path source = Paths.get(file.getAbsolutePath());

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(Files.probeContentType(source));
            metadata.setContentLength(file.length());

            try (InputStream inputStream = new FileInputStream(file)) {
                amazonS3.putObject(new PutObjectRequest(bucketName, s3FileName, inputStream, metadata)
                        .withCannedAcl(CannedAccessControlList.Private)); // 액세스 권한 설정
            }
            log.info("File uploaded to S3: {}", s3FileName);
        } catch (Exception e) {
            log.error("Error uploading file to S3", e);
        }
    }
}

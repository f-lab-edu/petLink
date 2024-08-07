package com.petlink.api.storage.image;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.petlink.api.storage.dto.ResultObject;
import com.petlink.api.storage.dto.UploadObject;
import com.petlink.api.storage.exception.StorageException;
import com.petlink.api.storage.exception.StorageExceptionCode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageUtils {

    private static final String END_POINT = "https://kr.object.ncloudstorage.com";
    private static final String REGION_NAME = "kr-standard";

    @Value("${storage.access-key}")
    private String accessKey;
    @Value("${storage.secret-key}")
    private String secretKey;
    private AmazonS3 s3;

    @PostConstruct
    void init() {
        s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(END_POINT, REGION_NAME))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();

    }

    public ResultObject uploadImage(UploadObject uploadObject) throws AmazonS3Exception, IOException {

        MultipartFile imageFile = uploadObject.getImageFile();

        if (imageFile == null || imageFile.isEmpty()) {
            throw new StorageException(StorageExceptionCode.NOT_FOUND_IMAGE_FILE);
        }

        String objectName = uploadObject.getObjectName();
        String bucketName = uploadObject.getBucket().getBucketName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageFile.getSize());
        metadata.setContentType(imageFile.getContentType());

        PutObjectRequest request = new PutObjectRequest(bucketName, objectName, imageFile.getInputStream(), metadata);
        request.setCannedAcl(CannedAccessControlList.PublicRead);

        PutObjectResult result = s3.putObject(request);

        if (result.getETag() == null || result.getETag().isBlank()) {
            throw new StorageException(StorageExceptionCode.FAILED_UPLOAD_IMAGE);
        }

        return ResultObject.builder()
                .objectName(objectName)
                .imageLink(getImageLink(bucketName, objectName))
                .build();
    }

    private String getImageLink(String bucketName, String objectName) {
        return END_POINT + "/" + bucketName + "/" + objectName;
    }
}

package com.petlink.common.storage.image;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.petlink.common.storage.dto.ResultObject;
import com.petlink.common.storage.dto.UploadObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUtils {

    private final String endPoint = "https://kr.object.ncloudstorage.com";
    private final String regionName = "kr-standard";
    @Value("${object-storage.access-key}")
    private String accessKey;
    @Value("${object-storage.secret-key}")
    private String secretKey;
    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
            .build();

    public ResultObject uploadImage(UploadObject uploadObject) throws Exception {

        MultipartFile imageFile = uploadObject.getImageFile();
        String objectName = uploadObject.getObjectName();
        String bucketName = uploadObject.getBucket().getBucketName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageFile.getSize());
        metadata.setContentType(imageFile.getContentType());

        PutObjectRequest request = new PutObjectRequest(bucketName, objectName, imageFile.getInputStream(), metadata);
        request.setCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(request);

        return ResultObject.builder()
                .objectName(objectName)
                .imageLink(getImageLink(bucketName, objectName))
                .build();
    }

    private String getImageLink(String bucketName, String objectName) {
        return endPoint + "/" + bucketName + "/" + objectName;
    }
}

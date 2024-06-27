package com.petlink.common.storage.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.petlink.common.storage.dto.ResultObject;
import com.petlink.common.storage.dto.UploadObject;
import com.petlink.common.storage.exception.StorageException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static com.petlink.common.storage.exception.StorageExceptionCode.FAILED_UPLOAD_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageUtilsTest {


    @InjectMocks
    private ImageUtils imageUtils;

    @Mock
    private AmazonS3 s3;

    @Test
    @DisplayName("이미지를 업로드 할 수 있다.")
    void uploadImageTest() throws AmazonS3Exception, IOException {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image".getBytes());
        UploadObject uploadObject = UploadObject.builder()
                .bucket(Bucket.IMAGE)
                .objectName("2021/08/25/image.jpg")
                .imageFile(mockMultipartFile)
                .build();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(mockMultipartFile.getSize());
        metadata.setContentType(mockMultipartFile.getContentType());

        PutObjectResult result = new PutObjectResult();
        result.setETag("etag");

        // Stubbing
        when(s3.putObject(any(PutObjectRequest.class))).thenReturn(result);

        // when
        ResultObject uploadedResult = imageUtils.uploadImage(uploadObject);

        // then
        assertThat(uploadedResult).isNotNull();
        assertThat(uploadedResult.getImageLink()).isNotNull();
        assertThat(uploadedResult.getObjectName()).isEqualTo("2021/08/25/image.jpg");
    }

    @Test
    @DisplayName("이미지 업로드 실패시 예외가 발생한다.")
    void uploadImageFailTest() throws AmazonS3Exception {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image".getBytes());
        UploadObject uploadObject = UploadObject.builder()
                .bucket(Bucket.IMAGE)
                .objectName("2021/08/25/image.jpg")
                .imageFile(mockMultipartFile)
                .build();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(mockMultipartFile.getSize());
        metadata.setContentType(mockMultipartFile.getContentType());

        PutObjectResult result = new PutObjectResult();
        result.setETag("");

        // Stubbing
        when(s3.putObject(any(PutObjectRequest.class))).thenThrow(new StorageException(FAILED_UPLOAD_IMAGE));

        // when, then
        assertThrows(StorageException.class, () -> imageUtils.uploadImage(uploadObject));
    }

    @Test
    @DisplayName("이미지를 업로드 할 수 있다.")
    void ifImageFileIsNull() throws AmazonS3Exception {
        // given
        UploadObject uploadObject = UploadObject.builder()
                .bucket(Bucket.IMAGE)
                .objectName("2021/08/25/image.jpg")
                .imageFile(null)
                .build();

        // when, then
        assertThrows(StorageException.class, () -> imageUtils.uploadImage(uploadObject));
    }
}
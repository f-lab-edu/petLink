package com.petlink.image.service;


import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.petlink.common.storage.dto.ResultObject;
import com.petlink.common.storage.exception.StorageException;
import com.petlink.common.storage.image.ImageUtils;
import com.petlink.image.domain.Image;
import com.petlink.image.dto.ImageDto;
import com.petlink.image.repository.ImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageUtils imageUtils;

    @Test
    @DisplayName("이미지와 파일명을 받아서 이미지 서버에 업로드 할 수 있다.")
    void uploadToImageTest() throws IOException, AmazonS3Exception {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image".getBytes());
        ImageDto imageDto = new ImageDto(mockMultipartFile, "image.jpg");
        ResultObject result = ResultObject.builder()
                .imageLink("https://image.petlink.kr/2021/08/25/image.jpg")
                .objectName("2021/08/25/image.jpg")
                .build();

        //when
        when(imageUtils.uploadImage(any())).thenReturn(result);

        //then
        ResultObject actual = imageService.uploadToImageServer(imageDto);
        assertEquals(result, actual);
    }

    @Test
    @DisplayName("이미지가 널일 경우 예외가 발생한다.")
    void uploadToImageExceptionTest() throws IOException, AmazonS3Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image".getBytes());
        ImageDto imageDto = new ImageDto(image, "image.jpg");

        //when
        when(imageUtils.uploadImage(any())).thenThrow(StorageException.class);

        //then
        assertThrows(StorageException.class, () -> imageService.uploadToImageServer(imageDto));
    }

    @Test
    @DisplayName("이미지 파일 이름이 null일 경우 예외가 발생한다.")
    void uploadToImageServerWithNullFileNameTest() {
        //given
        MockMultipartFile image = new MockMultipartFile("image", null, "image/jpeg", "image".getBytes());

        //when & then
        assertThrows(StorageException.class, () -> new ImageDto(image, null));
    }

    @Test
    @DisplayName("이미지 파일 이름이 빈 문자열일 경우 예외가 발생한다.")
    void uploadToImageServerWithEmptyFileNameTest() throws AmazonS3Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "", "image/jpeg", "image".getBytes());

        //when & then
        assertThrows(StorageException.class, () -> new ImageDto(image, null));
    }

    @Test
    @DisplayName("ResultObject가 null인 경우 예외 발생")
    void saveImageInfoWithNullResultObjectTest() {
        //given
        ResultObject resultObject = null;

        //when & then
        assertThrows(StorageException.class, () -> imageService.saveImageInfo(resultObject));
    }

    @Test
    @DisplayName("이미지 링크가 null인 경우 예외 발생")
    void saveImageInfoWithNullImageLinkTest() {
        //given
        ResultObject resultObject = ResultObject.builder()
                .imageLink(null)
                .objectName("2021/08/25/image.jpg")
                .build();

        //when & then
        assertThrows(StorageException.class, () -> imageService.saveImageInfo(resultObject));
    }

    @Test
    @DisplayName("이미지 링크가 비어 있는 경우 예외 발생")
    void saveImageInfoWithEmptyImageLinkTest() {
        //given
        ResultObject resultObject = ResultObject.builder()
                .imageLink("")
                .objectName("2021/08/25/image.jpg")
                .build();

        //when & then
        assertThrows(StorageException.class, () -> imageService.saveImageInfo(resultObject));
    }

    @Test
    @DisplayName("객체 이름이 null인 경우 예외 발생")
    void saveImageInfoWithNullObjectNameTest() {
        //given
        ResultObject resultObject = ResultObject.builder()
                .imageLink("https://image.petlink.kr/2021/08/25/image.jpg")
                .objectName(null)
                .build();

        //when & then
        assertThrows(StorageException.class, () -> imageService.saveImageInfo(resultObject));
    }

    @Test
    @DisplayName("객체 이름이 비어 있는 경우 예외 발생")
    void saveImageInfoWithEmptyObjectNameTest() {
        //given
        ResultObject resultObject = ResultObject.builder()
                .imageLink("https://image.petlink.kr/2021/08/25/image.jpg")
                .objectName("")
                .build();

        //when & then
        assertThrows(StorageException.class, () -> imageService.saveImageInfo(resultObject));
    }

    @Test
    @DisplayName("이미지 정보가 정상적으로 저장되는 경우")
    void saveImageInfoSuccessfullyTest() {
        //given
        ResultObject resultObject = ResultObject.builder()
                .imageLink("https://image.petlink.kr/2021/08/25/image.jpg")
                .objectName("2021/08/25/image.jpg")
                .build();

        Image expectedImage = Image.builder()
                .path("https://image.petlink.kr/2021/08/25/image.jpg")
                .name("2021/08/25/image.jpg")
                .description("펀딩 이미지")
                .build();

        when(imageRepository.save(any())).thenReturn(expectedImage);

        //when
        Image actualImage = imageService.saveImageInfo(resultObject);

        //then
        assertEquals(expectedImage, actualImage);
    }
}
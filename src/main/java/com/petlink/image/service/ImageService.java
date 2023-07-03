package com.petlink.image.service;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.petlink.common.storage.dto.ResultObject;
import com.petlink.common.storage.dto.UploadObject;
import com.petlink.common.storage.exception.StorageException;
import com.petlink.common.storage.image.Bucket;
import com.petlink.common.storage.image.ImageUtils;
import com.petlink.funding.domain.Image;
import com.petlink.funding.repository.ImageRepository;
import com.petlink.image.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.petlink.common.storage.exception.StorageExceptionCode.NOT_FOUND_FILE_NAME;
import static com.petlink.common.storage.exception.StorageExceptionCode.NOT_FOUND_IMAGE_FILE;
import static com.petlink.common.storage.exception.StorageExceptionCode.NOT_FOUND_IMAGE_LINK;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageUtils imageUtils;

    /**
     * 이미지 서버로 업로드
     * Upload to image server result object.
     *
     * @param imageDto the image dto
     * @return the result object
     * @throws IOException the io exception
     */
    @Transactional
    public ResultObject uploadToImageServer(ImageDto imageDto) throws IOException, AmazonS3Exception {
        if (imageDto.getImage() == null) {
            throw new StorageException(NOT_FOUND_IMAGE_FILE);
        }

        return imageUtils.uploadImage(UploadObject.builder()
                .objectName(getNameWithFolder(imageDto.getObjectName()))
                .imageFile(imageDto.getImage())
                .bucket(Bucket.IMAGE)
                .build());
    }

    /**
     * 이미지 경로를 생성 년월/일/파일명
     * Gets name with folder.
     *
     * @param fileName the file name
     * @return the name with folder
     */
    private String getNameWithFolder(String fileName) {
        if (fileName.isEmpty()) {
            throw new StorageException(NOT_FOUND_FILE_NAME);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM/dd/");
        String toDay = LocalDate.now().format(formatter);
        return toDay + fileName;
    }

    /**
     * 오브젝트 스토리지에 업로드된 이미지 정보를 DB에 저장
     * Save image info image.
     *
     * @param resultObject the result object
     * @return the image
     */
    public Image saveImageInfo(ResultObject resultObject) {
        String link = resultObject.getImageLink();
        if (link.isEmpty()) {
            throw new StorageException(NOT_FOUND_FILE_NAME);
        }
        String name = resultObject.getObjectName();
        if (name.isEmpty()) {
            throw new StorageException(NOT_FOUND_IMAGE_LINK);
        }

        return imageRepository.save(Image.builder()
                .path(link)
                .name(name)
                .description("펀딩 이미지")
                .build());
    }

}


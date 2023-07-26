package com.petlink.image.dto;

import com.petlink.common.storage.exception.StorageException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

import static com.petlink.common.storage.exception.StorageExceptionCode.NOT_FOUND_FILE_NAME;
import static com.petlink.common.storage.exception.StorageExceptionCode.NOT_FOUND_IMAGE_FILE;

@Getter
public class ImageDto implements Serializable {
    MultipartFile image;
    String objectName;

    public ImageDto(MultipartFile image, String objectName) {
        if (image == null || image.isEmpty()) {
            throw new StorageException(NOT_FOUND_IMAGE_FILE);
        }
        if (objectName == null || objectName.trim().isEmpty()) {
            throw new StorageException(NOT_FOUND_FILE_NAME);
        }
        this.image = image;
        this.objectName = objectName;
    }
}

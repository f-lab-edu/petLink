package com.petlink.api.image.dto;

import com.petlink.api.storage.exception.StorageException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

import static com.petlink.api.storage.exception.StorageExceptionCode.NOT_FOUND_FILE_NAME;
import static com.petlink.api.storage.exception.StorageExceptionCode.NOT_FOUND_IMAGE_FILE;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageDto implements Serializable {
    MultipartFile image;
    String objectName;

    public ImageDto(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new StorageException(NOT_FOUND_IMAGE_FILE);
        }
        if (image.getName().trim().isEmpty()) {
            throw new StorageException(NOT_FOUND_FILE_NAME);
        }
        this.image = image;
        this.objectName = image.getName();
    }

    public static ImageDto of(MultipartFile image) {
        return ImageDto.builder()
                .image(image)
                .objectName(image.getName())
                .build();
    }
}

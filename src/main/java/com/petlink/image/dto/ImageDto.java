package com.petlink.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@AllArgsConstructor()
public class ImageDto implements Serializable {
    MultipartFile image;
    String objectName;
}

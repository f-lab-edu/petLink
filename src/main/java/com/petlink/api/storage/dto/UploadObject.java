package com.petlink.api.storage.dto;

import com.petlink.api.storage.image.Bucket;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class UploadObject {
    MultipartFile imageFile;
    String objectName;
    Bucket bucket;
}

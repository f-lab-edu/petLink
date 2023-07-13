package com.petlink.common.storage.dto;

import com.petlink.common.storage.image.Bucket;
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

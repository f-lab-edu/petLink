package com.petlink.api.storage.image;

import lombok.Getter;

@Getter
public enum Bucket {

    IMAGE("petlink-images-buket");
    final String bucketName;

    Bucket(String bucketName) {
        this.bucketName = bucketName;
    }
}

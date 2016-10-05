package com.gallery.example.models;

import java.util.List;

/**
 * Created by seogangmin on 2016. 9. 22..
 */

public class Album {
    protected String bucketName;
    protected List<MediaObject> mediaObjects;
    protected MediaObject thumbMedia;
    protected int totalCount;

    public Album() {}

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public List<MediaObject> getMediaObjects() {
        return mediaObjects;
    }

    public void setMediaObjects(List<MediaObject> mediaObjects) {
        this.mediaObjects = mediaObjects;
    }

    public MediaObject getThumbMedia() {
        return mediaObjects.get(0);
    }

    public int getTotalCount() {
        return mediaObjects.size();
    }
}
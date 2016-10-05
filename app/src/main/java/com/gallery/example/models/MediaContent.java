package com.gallery.example.models;

import android.provider.MediaStore;

import com.gallery.example.utils.MediaType;

/**
 * Created by seogangmin on 2016. 9. 22..
 */

public class MediaContent {

    private long id;
    private String filePath;
    private long createdDt;
    private String mediaType;
    private String mimeType;
    private String title;

    public long getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(long createdDt) {
        this.createdDt = createdDt;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(int type) {
        switch (type){
            case MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE :{
                this.mediaType = MediaType.IMAGE.toString();
                break;
            }
            case MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO :{
                this.mediaType = MediaType.VIDEO.toString();
                break;
            }
        }
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
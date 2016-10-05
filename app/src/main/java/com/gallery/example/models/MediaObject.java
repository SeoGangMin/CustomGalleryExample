package com.gallery.example.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.gallery.example.utils.MediaType;

/**
 * Created by seogangmin on 2016. 9. 22..
 */

public class MediaObject{

    private long id;
    private long bucketId;
    private String bucketName;
    private long dateTaken;
    private String filePath;
    private String mimeType;

    private String mediaType;

    private double lat;
    private double lon;

    private String thumbnailPath;

    private int duration;   //if video

    private int orientation; //if image

    public MediaObject() {}

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getMediaType() {
        return (this.mimeType.indexOf(MediaType.IMAGE.toString()) > -1) ? MediaType.IMAGE.toString() : MediaType.VIDEO.toString();
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}

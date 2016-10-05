package com.gallery.example.utils;

/**
 * Created by seogangmin on 2016. 9. 22..
 */

public enum  MediaType {
    IMAGE ,VIDEO;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

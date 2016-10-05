package com.gallery.example.utils;

import com.gallery.example.models.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seogangmin on 2016. 9. 24..
 */

public class DataHolder {
    private static List<Album> albumList = new ArrayList<Album>();

    public static List<Album> getAlbumList() {
        return albumList;
    }

    public static Album getSelectedAlbum(int position){
        return albumList.get(position);
    }

    public static void setAlbumList(List<Album> albumList) {
        DataHolder.albumList = albumList;
    }
}
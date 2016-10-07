package com.gallery.example.utils;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.gallery.example.models.Album;
import com.gallery.example.models.MediaObject;
import com.google.gson.Gson;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by seogangmin on 2016. 9. 22..
 */

public class ContentManager {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;

    public ContentManager(Context context) {
        this.mContext = context;
    }

    /**
     * find image list from external storage
     *
     * @return list
     */
    private List<MediaObject> getImageContent(){
        List<MediaObject> list = null;
        String[] projection = {
                MediaStore.Images.ImageColumns._ID
                ,MediaStore.Images.ImageColumns.BUCKET_ID
                ,MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
                ,MediaStore.Images.ImageColumns.DATE_TAKEN
                ,MediaStore.Images.ImageColumns.DATA
                ,MediaStore.Images.ImageColumns.MIME_TYPE
                ,MediaStore.Images.ImageColumns.LATITUDE
                ,MediaStore.Images.ImageColumns.LONGITUDE
                ,MediaStore.Images.ImageColumns.ORIENTATION
        };

        //        StringBuffer selection = new StringBuffer();

        Uri queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = mContext.getContentResolver()
                .query(queryUri, projection, null, null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

        if(cursor.getCount() > 0){
            list = extractMediaObjectList(cursor);
        }

        String jsonList = new Gson().toJson(list);
        Log.d(TAG,"============ PHOTO ============");
        Log.d(TAG, jsonList.toString());
        Log.d(TAG,"============ PHOTO ============");

        return list;
    }


    /**
     * find video list from external storage
     *
     * @return list
     */
    private List<MediaObject> getVideoContent(){
        List<MediaObject> list = null;
        String[] projection = {
                MediaStore.Video.VideoColumns._ID
                ,MediaStore.Video.VideoColumns.BUCKET_ID
                ,MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME
                ,MediaStore.Video.VideoColumns.DATE_TAKEN
                ,MediaStore.Video.VideoColumns.DATA
                ,MediaStore.Video.VideoColumns.MIME_TYPE
                ,MediaStore.Video.VideoColumns.LATITUDE
                ,MediaStore.Video.VideoColumns.LONGITUDE
                ,MediaStore.Video.VideoColumns.DURATION
        };

//        StringBuffer selection = new StringBuffer();


        Uri queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = mContext.getContentResolver()
                .query(queryUri, projection, null, null, MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC");

        if(cursor.getCount() > 0){
            list = extractMediaObjectList(cursor);
        }

        String jsonList = new Gson().toJson(list);

        Log.d(TAG,"============ VIDEO ============");
        Log.d(TAG, jsonList.toString());
        Log.d(TAG,"============ VIDEO ============");

        return list;
    }

    public String getImageThumbnailPath(long imageId){
        String thumbnailPath = null;
        String[] projection = {
                MediaStore.Images.Thumbnails._ID
                ,MediaStore.Images.Thumbnails.IMAGE_ID
                ,MediaStore.Images.Thumbnails.KIND
                ,MediaStore.Images.Thumbnails.DATA
        };

        StringBuffer selection = new StringBuffer();
        selection.append(MediaStore.Images.Thumbnails.IMAGE_ID);
        selection.append("=");
        selection.append(imageId);

        Uri queryUri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;

        Cursor cursor = mContext.getContentResolver()
                .query(queryUri, projection, selection.toString(), null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            thumbnailPath = cursor.getString(3);
        }
        cursor.close();

        return thumbnailPath;
    }


    /**
     * find media list from cursor
     *
     * @param cursor
     */
    private List<MediaObject> extractMediaObjectList(Cursor cursor){
        List<MediaObject> list = new ArrayList<MediaObject>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id       = cursor.getLong(0);
                long bucketId = cursor.getLong(1);
                String bucketName = cursor.getString(2);
                long dateTaken  = cursor.getLong(3);
                String filePath = cursor.getString(4);
                String mimeType = cursor.getString(5);
                double lat      = cursor.getDouble(6);
                double lon      = cursor.getDouble(7);

                MediaObject mediaObject = new MediaObject();
                mediaObject.setId(id);
                mediaObject.setBucketId(bucketId);
                mediaObject.setBucketName(bucketName);
                mediaObject.setDateTaken(dateTaken);
                mediaObject.setFilePath(filePath);
                mediaObject.setMimeType(mimeType);
                mediaObject.setLat(lat);
                mediaObject.setLon(lon);

                String thumbnailPath = null;


                if(mediaObject.getMediaType().equals(MediaType.IMAGE.toString())){
                    mediaObject.setOrientation(cursor.getInt(8));
                    thumbnailPath = getImageThumbnailPath(id);
                }else{
                    mediaObject.setDuration(cursor.getInt(8));
                    thumbnailPath = getVideoThumbnailPath(id);
                }

                mediaObject.setThumbnailPath(thumbnailPath);

                list.add(mediaObject);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }


    public String getVideoThumbnailPath(long videoId){
        String thumbnailPath = null;
        String[] projection = {
                MediaStore.Video.Thumbnails._ID
                ,MediaStore.Video.Thumbnails.VIDEO_ID
                ,MediaStore.Video.Thumbnails.KIND
                ,MediaStore.Video.Thumbnails.DATA
        };

        StringBuffer selection = new StringBuffer();
        selection.append(MediaStore.Video.Thumbnails.VIDEO_ID);
        selection.append("=");
        selection.append(videoId);

        Uri queryUri = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;

        Cursor cursor = mContext.getContentResolver()
                .query(queryUri, projection, selection.toString(), null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            thumbnailPath = cursor.getString(3);
        }
        cursor.close();

        return thumbnailPath;
    }


    public Map<String, List<MediaObject>> mediaObjectGroups(){
        Map<String, List<MediaObject>> map = new HashMap<>();

        List<MediaObject> combineList = new ArrayList<MediaObject>();
        combineList.addAll(getImageContent());
        combineList.addAll(getVideoContent());

        ListIterator<MediaObject> it = combineList.listIterator();

        while (it.hasNext()){
            MediaObject mediaObject = it.next();
            String key = mediaObject.getBucketName();
            List<MediaObject> value = null;

            if(map.get(key) == null){
                value = new ArrayList<MediaObject>();
                value.add(mediaObject);
                map.put(key, value);
            }else{
                map.get(key).add(mediaObject);
            }
        }

        map = new TreeMap<String, List<MediaObject>>(map);

        return map;
    }

    public List<Album> getAlbumList(){
        List<Album> list = new ArrayList<Album>();
        Map<String, List<MediaObject>> mediaObjectGroups = mediaObjectGroups();

        Iterator<String> it = mediaObjectGroups.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            Album album = new Album();
            album.setBucketName(key);

            List<MediaObject> mediaObjects = mediaObjectGroups.get(key);

            Collections.sort(mediaObjects, new MediaObjectsComparator());

            album.setMediaObjects(mediaObjects);
            list.add(album);
        }

        return list;
    }

    static class MediaObjectsComparator implements Comparator<MediaObject>{

        @Override
        public int compare(MediaObject obj1, MediaObject obj2) {
            return obj1.getDateTaken() > obj2.getDateTaken() ? -1 : obj1.getDateTaken() < obj2.getDateTaken() ? 1:0;
        }
    }


}

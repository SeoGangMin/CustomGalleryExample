package com.gallery.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.gallery.example.R;
import com.gallery.example.models.MediaObject;
import com.gallery.example.utils.MediaType;
import com.gallery.example.utils.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seogangmin on 2016. 9. 24..
 */

public class AlbumGalleryAdapter  extends BaseGalleryAdapter {

    private final String TAG = this.getClass().getSimpleName();

    public AlbumGalleryAdapter(Context context, List items) {
        super(context, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final MediaObject item = (MediaObject) mItems.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_album_gallery, parent, false);
            convertView.setLayoutParams( new GridView.LayoutParams( SQAURE_SIZE, SQAURE_SIZE ) );

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String filePath = item.getFilePath();

        if(MediaType.VIDEO.toString().equals(item.getMediaType())){
            filePath = item.getThumbnailPath();
            holder.icVideoPlay.setVisibility(View.VISIBLE);
        }else{
            holder.icVideoPlay.setVisibility(View.GONE);
        }

        if(item.isSelected()){
            holder.icChecked.setImageResource(R.drawable.ic_check_enabled);
        }else{
            holder.icChecked.setImageResource(R.drawable.ic_check_disabled);
        }

        Picasso.with(mContext)
                .load(new File(filePath))
                .resize(SQAURE_SIZE, SQAURE_SIZE)
                .error(R.drawable.ic_warning)
                .centerCrop()
                .into(holder.thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        ((MediaObject) mItems.get(position)).setError(true);
                    }
                });

        return convertView;
    }

    static class ViewHolder{
        ImageView thumbnail;
        ImageView icVideoPlay;
        ImageView icChecked;

        public ViewHolder(View view){
            this.thumbnail   = (ImageView) view.findViewById(R.id.thumbnail);
            this.icVideoPlay = (ImageView) view.findViewById(R.id.ic_video_play);
            this.icChecked   = (ImageView) view.findViewById(R.id.ic_checked);
        }
    }

}
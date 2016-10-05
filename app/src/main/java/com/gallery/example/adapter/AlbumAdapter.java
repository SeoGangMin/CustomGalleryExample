package com.gallery.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gallery.example.R;
import com.gallery.example.models.Album;
import com.gallery.example.models.MediaObject;
import com.gallery.example.utils.MediaType;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by seogangmin on 2016. 9. 24..
 */

public class AlbumAdapter extends BaseGalleryAdapter {

    private final String TAG = this.getClass().getSimpleName();

    public AlbumAdapter(Context context, List<Album> items) {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final Album item = (Album) mItems.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_album, parent, false);
            convertView.setLayoutParams( new GridView.LayoutParams( SQAURE_SIZE, SQAURE_SIZE ) );

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        MediaObject mediaObject = item.getThumbMedia();

        String filePath = mediaObject.getFilePath();

        if(MediaType.VIDEO.toString().equals(mediaObject.getMediaType())){
            filePath = mediaObject.getThumbnailPath();
            holder.icVideoPlay.setVisibility(View.VISIBLE);
        }

        Picasso.with(mContext)
                .load(new File(filePath))
                .resize(SQAURE_SIZE, SQAURE_SIZE)
                .centerCrop()
                .into(holder.thumbnail);

        if(MediaType.VIDEO.toString().equals(mediaObject.getMediaType())){
            holder.icVideoPlay.setVisibility(View.VISIBLE);
        }

        holder.mediaCount.setText(String.valueOf(item.getTotalCount()));
        holder.groupName.setText(item.getBucketName());

        return convertView;
    }


    static class ViewHolder{
        ImageView thumbnail;
        ImageView icVideoPlay;
        TextView mediaCount;
        TextView groupName;

        public ViewHolder(View view){
            this.thumbnail   = (ImageView) view.findViewById(R.id.thumbnail);
            this.icVideoPlay = (ImageView) view.findViewById(R.id.ic_video_play);
            this.mediaCount  = (TextView) view.findViewById(R.id.media_count);
            this.groupName   = (TextView) view.findViewById(R.id.group_name);
        }
    }
}

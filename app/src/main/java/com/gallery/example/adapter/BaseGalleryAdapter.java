package com.gallery.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gallery.example.R;
import com.gallery.example.models.Album;
import com.gallery.example.models.MediaObject;
import com.gallery.example.utils.MediaType;
import com.gallery.example.utils.Util;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

/**
 * Created by seogangmin on 2016. 9. 24..
 */

public abstract class BaseGalleryAdapter  extends BaseAdapter {

    protected final int SAMPLE_SIZE = 2;
    protected final int SQAURE_SIZE;
    protected final BitmapFactory.Options BITMAP_OPTS;

    protected Context mContext;
    protected List mItems;

    public BaseGalleryAdapter(Context context, List items) {
        this.mContext = context;
        this.mItems = items;
        this.SQAURE_SIZE = Util.getDisplayMetrics(context).widthPixels/context.getResources().getInteger(R.integer.grid_column_count);
        this.BITMAP_OPTS = new BitmapFactory.Options();
        BITMAP_OPTS.inSampleSize = SAMPLE_SIZE;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
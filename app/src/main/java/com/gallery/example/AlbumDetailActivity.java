package com.gallery.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.gallery.example.adapter.AlbumGalleryAdapter;
import com.gallery.example.models.Album;
import com.gallery.example.models.MediaObject;
import com.google.gson.Gson;

/**
 * Created by seogangmin on 2016. 9. 24..
 */

public class AlbumDetailActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private Album mAlbum;
    private GridView mGridview;
    private AlbumGalleryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_frame);

        String data = getIntent().getStringExtra("data");

        mAlbum = new Gson().fromJson(data, Album.class);

        bindActivity();

        setGridView();
    }

    private void bindActivity(){
        mGridview = (GridView) findViewById(R.id.grid_view);
    }

    private void setGridView(){
        mAdapter = new AlbumGalleryAdapter(this, mAlbum.getMediaObjects());
        mGridview.setAdapter(mAdapter);

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaObject selected = mAlbum.getMediaObjects().get(position);
                int orientation = selected.getOrientation();
                Toast.makeText(AlbumDetailActivity.this, String.format("orientation:%d", orientation), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

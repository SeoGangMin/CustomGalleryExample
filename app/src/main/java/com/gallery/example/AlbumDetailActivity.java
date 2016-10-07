package com.gallery.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gallery.example.adapter.AlbumGalleryAdapter;
import com.gallery.example.models.Album;
import com.gallery.example.models.MediaObject;
import com.gallery.example.utils.DataHolder;
import com.google.gson.Gson;

/**
 * Created by seogangmin on 2016. 9. 24..
 */

public class AlbumDetailActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private Album mAlbum;
    private LinearLayout mProgressLayout;
    private GridView mGridview;
    private AlbumGalleryAdapter mAdapter;

    private final int LIMIT_SELECT_COUNT = 50;
    private int mSelectedCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_frame);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int position = getIntent().getIntExtra("data", 0);

        mAlbum = DataHolder.getSelectedAlbum(position);

        setActionBarTitle(mSelectedCount);

        bindActivity();
        setGridView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_complete : {
                Toast.makeText(AlbumDetailActivity.this, "complete", Toast.LENGTH_SHORT).show();
                break;
            }
            case  android.R.id.home : {
                AlbumDetailActivity.this.finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setActionBarTitle(int count){
        getSupportActionBar().setTitle(String.format("%s (%d/%d)", mAlbum.getBucketName(), count, LIMIT_SELECT_COUNT));
    }

    private void bindActivity(){
        mGridview = (GridView) findViewById(R.id.grid_view);
        mProgressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        mGridview.setVisibility(View.VISIBLE);
        mProgressLayout.setVisibility(View.GONE);
    }

    private void setGridView(){
        mAdapter = new AlbumGalleryAdapter(this, mAlbum.getMediaObjects());
        mGridview.setAdapter(mAdapter);

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaObject selected = mAlbum.getMediaObjects().get(position);
                if(selected.isSelected()){
                    if(mSelectedCount > 0){
                        mAlbum.getMediaObjects().get(position).setSelected(false);
                        mSelectedCount--;
                    }
                }else{
                    if(mSelectedCount < LIMIT_SELECT_COUNT){
                        if(!mAlbum.getMediaObjects().get(position).isError()){
                            mAlbum.getMediaObjects().get(position).setSelected(true);
                            mSelectedCount++;
                        }
                    }else{
                        Toast.makeText(AlbumDetailActivity.this, "선택가능한 최대개수를 초과하였습니다", Toast.LENGTH_SHORT).show();
                    }
                }
                setActionBarTitle(mSelectedCount);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        clearSelectedMedia();
        super.onDestroy();
    }

    private void clearSelectedMedia(){
        for(int i=0; i<mAlbum.getMediaObjects().size(); i++){
            mAlbum.getMediaObjects().get(i).setSelected(false);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}

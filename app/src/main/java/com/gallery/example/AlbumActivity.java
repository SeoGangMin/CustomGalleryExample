package com.gallery.example;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.gallery.example.adapter.AlbumAdapter;
import com.gallery.example.models.Album;
import com.gallery.example.utils.ContentManager;
import com.gallery.example.utils.DataHolder;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private List<Album> mAlbumList;
    private GridView mGridview;
    private AlbumAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_frame);

        bindActivity();
        permissionCheck();
        getMediaObjects();
    }

    private void bindActivity(){
        mGridview = (GridView) findViewById(R.id.grid_view);
    }

    private void permissionCheck(){
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };


    private void getMediaObjects(){
        ContentManager contentManager = new ContentManager(this);
        mAlbumList = contentManager.getAlbumList();
        DataHolder.setAlbumList(mAlbumList);
        mAdapter = new AlbumAdapter(this, mAlbumList);
        mGridview.setAdapter(mAdapter);

        int width = mGridview.getColumnWidth();

        Log.d(TAG, String.format("width:%d", width));

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AlbumActivity.this, AlbumDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data", new Gson().toJson(mAlbumList.get(position)));
                startActivity(intent);
            }
        });
    }

}

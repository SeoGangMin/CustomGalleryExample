package com.gallery.example;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
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
    private LinearLayout mProgressLayout;
    private ContentManager mContentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_frame);
        bindActivity();
        mContentManager = new ContentManager(this);

        getSupportActionBar().setTitle(getString(R.string.main_title));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        permissionCheck();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            AlbumActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindActivity(){
        mGridview = (GridView) findViewById(R.id.grid_view);
        mProgressLayout = (LinearLayout) findViewById(R.id.progress_layout);
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
            new AlbumInitAsyncTask().execute();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };


    private class AlbumInitAsyncTask extends AsyncTask<Void, Void, List<Album>> {

        @Override
        protected List<Album> doInBackground(Void... params) {
            mAlbumList = mContentManager.getAlbumList();
            Log.d(TAG, String.format("albumSize : %d", mAlbumList.size()));
            return mAlbumList;
        }

        @Override
        protected void onPostExecute(final List<Album> albumList) {
            mProgressLayout.setVisibility(View.GONE);
            mGridview.setVisibility(View.VISIBLE);

            DataHolder.setAlbumList(albumList);
            mAdapter = new AlbumAdapter(AlbumActivity.this, albumList);
            mGridview.setAdapter(mAdapter);
            mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(AlbumActivity.this, AlbumDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("data", position);
                    startActivity(intent);
                }
            });
        }
    }

}
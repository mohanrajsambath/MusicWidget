package com.androidtask.musicwidget.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.PermissionChecker;

import com.androidtask.musicwidget.MusicWidget;
import com.androidtask.musicwidget.R;
import com.androidtask.musicwidget.backend.SongListAdapter;
import com.androidtask.musicwidget.backend.SongListLoader;
import com.androidtask.musicwidget.model.Song;
import com.androidtask.musicwidget.service.MusicService;
import com.androidtask.musicwidget.utilis.Constants;
import com.androidtask.musicwidget.utilis.PermissionHelper.ActivityManagePermission;
import com.androidtask.musicwidget.utilis.PermissionHelper.PermissionResult;

/**
 * The type Song list activity.
 */
public class SongListActivity extends ActivityManagePermission {
    private SongListAdapter adapter;
    private Context getActivityContext;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        getActivityContext = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_song_list));
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        permissionCheck();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void permissionCheck() {

        if (getActivityContext != null) {
            askCompactPermissions(Constants.PERMISSION_LIST, new PermissionResult() {

                @Override
                public void permissionGranted() {
                    initView();
                    initLoadSongsList();
                }

                @Override
                public void permissionDenied() {
                    Toast.makeText(getActivityContext, "The app was not allowed to read or write to your storage. Hence,Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }

                @Override
                public void permissionForeverDenied() {
                    Toast.makeText(getActivityContext, "The app was not allowed to read or write to your storage. Hence,Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void initView() {
         list = findViewById(R.id.listView);
    }


    private void initLoadSongsList() {
        Cursor listCursor = SongListLoader.getInstance(this).getCursor();
        adapter = new SongListAdapter(this, listCursor);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return SongListLoader.getInstance(SongListActivity.this).getFilteredCursor(constraint);
            }
        });
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = ((SongListAdapter) parent.getAdapter()).getSong(position);
                Log.i("SongListActivity click", song.toString());
                Intent serviceIntent = new Intent(SongListActivity.this, MusicService.class);
                serviceIntent.setAction(MusicWidget.ACTION_JUMP_TO);
                serviceIntent.putExtra("song", song);
                SongListActivity.this.startService(serviceIntent);
                finish();
            }
        });

    }


}

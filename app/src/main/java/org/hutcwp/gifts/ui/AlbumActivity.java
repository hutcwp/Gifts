package org.hutcwp.gifts.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.adapter.AlbumAdapter;
import org.hutcwp.gifts.app.AppGlobal;

import java.util.Arrays;

public class AlbumActivity extends AppCompatActivity {

    private AlbumAdapter adapter;
    RecyclerView rvAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        rvAlbum = (RecyclerView) findViewById(R.id.rv_album);

        adapter = new AlbumAdapter(AlbumActivity.this, Arrays.asList(AppGlobal.IMGS_SPANNER));

        rvAlbum.setAdapter(adapter);

    }


}

package com.app.julie.oneproject.business.photo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.julie.common.imageloader.ImageLoaderManager;
import com.app.julie.oneproject.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "image_url";
    private String url;

    public static void start(Context context, String url) {
        Intent starter = new Intent(context, PhotoActivity.class);
        starter.putExtra(IMAGE_URL, url);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString(IMAGE_URL);
        }
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);

        ViewCompat.setTransitionName(photoView, "target");

        ImageLoaderManager.getInstance().load(photoView, url);
    }
}

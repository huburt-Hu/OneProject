package com.app.julie.oneproject.business.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.app.julie.common.download.DownloadManager;
import com.app.julie.common.download.DownloadProgressListener;
import com.app.julie.common.download.UpdateApkService;
import com.app.julie.common.util.LogUtils;
import com.app.julie.oneproject.R;

import java.io.File;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class WaveBySinCosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_by_sin_cos);

        File file = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "app.apk");

        String baseUrl = "http:/192.168.31.243:8080/";
        String url = "resources/app.apk";

        UpdateApkService.startDownload(this, R.mipmap.icon_launcher, baseUrl, url, file);
    }
}
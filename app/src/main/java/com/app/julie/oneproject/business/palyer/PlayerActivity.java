package com.app.julie.oneproject.business.palyer;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.app.julie.common.base.BaseActivity;
import com.app.julie.common.imageloader.ImageLoaderManager;
import com.app.julie.oneproject.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by hubert
 * <p>
 * Created on 2017/6/26.
 */

public class PlayerActivity extends BaseActivity {

    public static final String TITLE = "title";
    public static final String IMAGE = "image";
    public static final String URL = "url";
    JCVideoPlayer.JCAutoFullscreenListener sensorEventListener;
    SensorManager sensorManager;
    private String url;
    private String title;
    private String image;
    private ImageView imageView;
    private ViewGroup root;

    public static void start(Context context, String title, String image, String url) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(IMAGE, image);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ijkplayer);

        initData();
//        url = "http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
//        title = "嫂子闭眼睛";
//        image = "http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640";

        getWindow().getEnterTransition().setDuration(500);

        root = (ViewGroup) findViewById(R.id.root);
        root.post(new Runnable() {
            @Override
            public void run() {
                animateRevealColorFromCoordinates(root, R.color.bg, root.getWidth() / 2, root.getHeight() / 2);
            }
        });

        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        //共享元素
        ViewCompat.setTransitionName(jcVideoPlayerStandard, "target");
        ViewCompat.setTransitionName(jcVideoPlayerStandard.titleTextView, "title");

        jcVideoPlayerStandard.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title);
        ImageLoaderManager.getInstance().load(jcVideoPlayerStandard.thumbImageView, image);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString(TITLE);
            image = extras.getString(IMAGE);
            url = extras.getString(URL);
        }
    }

    //
    private Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color, int x, int y) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        sensorManager.unregisterListener(sensorEventListener);
    }
}

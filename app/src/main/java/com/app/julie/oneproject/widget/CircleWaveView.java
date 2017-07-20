package com.app.julie.oneproject.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/7.
 */

public class CircleWaveView extends CustomView {

    private int color = 0xffffff;
    private int r;
    private float drawR;
    private int accuracy = 50;
    private long extend;
    private long fade;

    public CircleWaveView(Context context) {
        this(context, null);
    }

    public CircleWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDeafultPaint.setAntiAlias(true);
        mDeafultPaint.setColor(color);
        mDeafultPaint.setAlpha(150);
        mDeafultPaint.setStrokeMiter(10);
        mDeafultPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        r = Math.min(mViewHeight / 2, mViewHeight / 2);
        drawR = r * 0.6f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (current < extend) {
            mDeafultPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        } else {
            mDeafultPaint.setStyle(Paint.Style.STROKE);
        }
        canvas.drawCircle(mViewHeight / 2, mViewHeight / 2, drawR, mDeafultPaint);
    }

    private boolean toggle;
    private long s;
    private long current;

    public void startAnimation(long speed) {
        this.s = speed;
        extend = s * 3 / 4;
        fade = s / 2;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                toggle = true;
                current += accuracy;
                if (current > s) {
                    current = 0;
                    drawR = 0.6f * r;
                }
                calculate();
                invalidate();
                if (toggle) {
                    postDelayed(this, accuracy);
                }
            }
        }, accuracy);
    }

    private void calculate() {

        if (current < fade) {
            mDeafultPaint.setAlpha(150);
            drawR = drawR + r * 0.4f * accuracy / extend;
        } else if (current >= fade && current < extend) {
            drawR = drawR + r * 0.4f * accuracy / extend;
            mDeafultPaint.setAlpha((int) (150 - 150 * (current - fade) / (s - fade)));
        } else {
            mDeafultPaint.setAlpha((int) (150 - 150 * (current - fade) / (s - fade)));
        }
    }

    public void stopAnimation() {
        toggle = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }
}

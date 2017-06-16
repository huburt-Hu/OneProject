package com.app.julie.oneproject.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.app.julie.common.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubert
 * <p>
 * Created on 2017/6/7.
 */

public class TimePicker extends View implements View.OnTouchListener {

    public static final int TYPE_MOVE = 1;
    public static final int TYPE_EXTEND = 2;
    public static final int TYPE_CLICK = 3;

    private static String[] titles = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30"
            , "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};

    private static String subTitle = ":30";

    private int lineColor = Color.parseColor("#b3b3b3");

    private int lightTitleColor = Color.parseColor("#71baff");
    private int titleColor = Color.parseColor("#666666");

    private int textColor = Color.parseColor("#fefefe");
    private int bookColor = Color.parseColor("#a771baff");
    private int bookStrokeColor = Color.parseColor("#71baff");
    private int usedColor = Color.parseColor("#a7ff4081");
    private int usedStrokeColor = Color.parseColor("#FF4081");
    private float round = 10f;//区域圆角

    private float extendPointR = ConvertUtils.dp2px(8);//拉伸点半径
    private int space = ConvertUtils.dp2px(30);//刻度间隔
    private int offset = 100;//短线偏移量

    private boolean isFrist = true;//初始化padding和宽高值
    private int type;//移动.扩展拉伸.点击

    private Paint mPaint;
    private Point p1;
    private Point p2;
    private Rect titleBounds;
    private RectF bookRect;
    private RectF usedRect;
    private int paddingTop;
    private int paddingLeft;
    private int width;
    private float downY;
    private int bookStart = -1;
    private int bookCount = 0;
    private List<int[]> used = new ArrayList<>();
    private List<RectF> usedAreas = new ArrayList<>();
    private int lineNumber;
    private RectF extendPointRect;
    private float bottom;


    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(ConvertUtils.dp2px(12));

        titleBounds = new Rect();
        mPaint.getTextBounds(titles[0], 0, titles[0].length(), titleBounds);

        p1 = new Point();
        p2 = new Point();
        bookRect = new RectF();
        usedRect = new RectF();
    }

    public void setBookArea(int start, int count) {
        bookStart = start;
        bookCount = count;
        setBookRect(start, count);
        postInvalidate();
    }

    public void clearBookArea() {
        bookStart = -1;
        bookCount = 0;
        setBookRect(0, 0);
        postInvalidate();
    }

    public void addUsed(int[] area) {
        used.add(area);
        postInvalidate();
    }

    public void clearUsed() {
        used.clear();
        postInvalidate();
    }

    public int getTimeInt(String time) {
        for (int i = 0; i < titles.length; i++) {
            if (TextUtils.equals(titles[i], time)) {
                return i;
            }
        }
        return -1;
    }

    public int getBookCount() {
        return bookCount;
    }

    public int getBookStart() {
        return bookStart;
    }

    private String getTimeString(int start, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < titles.length; i++) {
            if (start == i) {
                sb.append(titles[i]);
                sb.append("~");
            } else if (start + count == i) {
                sb.append(titles[i]);
            }
        }
        return sb.toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 800;//wrap_content的宽
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = space * titles.length;//wrap_content的高
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFrist) {//初始化参数
            //处理padding
            paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            width = getWidth() - paddingLeft - paddingRight;
            int height = getHeight() - paddingTop - paddingBottom;

            lineNumber = titles.length;
            bookRect.set(paddingLeft + 150, paddingTop + space * bookStart
                    , width - 30, paddingTop + space * (bookStart + bookCount));

            usedRect.set(paddingLeft + 150, paddingTop, width - 30, paddingTop);

            bottom = paddingTop + space * (titles.length - 1);
            isFrist = false;
        }

        //画刻度线
        mPaint.setColor(lineColor);
        for (int i = 0; i < lineNumber; i++) {
            p1.set(i % 2 == 1 ? paddingLeft + offset : paddingLeft, paddingTop + space * i);
            p2.set(width, paddingTop + space * i);
            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, mPaint);
        }

        //画时间文字
        mPaint.setTextAlign(Paint.Align.LEFT);
        for (int i = 0; i < lineNumber; i++) {
            if (i >= bookStart && i <= bookStart + bookCount) {
                mPaint.setColor(lightTitleColor);
            } else {
                mPaint.setColor(titleColor);
            }
            if (i % 2 == 0) {
                canvas.drawText(titles[i], paddingLeft, paddingTop + titleBounds.height() * 1.2f + space * i, mPaint);
            } else {
                if (i == bookStart || i == bookStart + bookCount) {
                    canvas.drawText(subTitle, paddingLeft + titleBounds.width() / 2, paddingTop + titleBounds.height() * 1.2f + space * i, mPaint);
                }
            }
        }
        //画已使用区域
        usedAreas.clear();
        for (int[] ints : used) {
            RectF rectF = new RectF();
            rectF.set(usedRect.left, usedRect.top + space * ints[0]
                    , usedRect.right, usedRect.bottom + space * (ints[0] + ints[1]));
            usedAreas.add(rectF);
            drawUsedRect(rectF, canvas, mPaint, "会议室已预定 " + getTimeString(ints[0], ints[1]));
        }

        //画预定区域
        drawBookRect(canvas, mPaint, "会议室预定 " + getTimeString(bookStart, bookCount));
    }

    private void drawUsedRect(RectF rectF, Canvas canvas, Paint paint, String text) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(usedStrokeColor);
        canvas.drawRoundRect(rectF, round, round, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(usedColor);
        canvas.drawRoundRect(rectF, round, round, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(textColor);
        canvas.drawText(text, rectF.centerX(), rectF.centerY(), paint);
    }

    public void drawBookRect(Canvas canvas, Paint paint, String text) {
        if (bookCount == 0) {
            return;
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(bookStrokeColor);
        canvas.drawRoundRect(bookRect, round, round, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bookColor);
        canvas.drawRoundRect(bookRect, round, round, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(textColor);
        canvas.drawText(text, bookRect.centerX(), bookRect.centerY(), paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(bookRect.centerX(), bookRect.bottom, extendPointR, paint);
        paint.setColor(bookStrokeColor);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(bookRect.centerX(), bookRect.bottom, extendPointR, paint);

        extendPointRect = new RectF(bookRect.centerX() - extendPointR * 2, bookRect.bottom - extendPointR * 2
                , bookRect.centerX() + extendPointR * 2, bookRect.bottom + extendPointR * 2);
        //查看扩展点触发区域
//        paint.setColor(Color.BLACK);
//        Log.i("tag", extendPointRect.toString());
//        canvas.drawRect(extendPointRect, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //view独享事件，即父view不可以获取后续事件，scrollview默认是false
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                downY = event.getY();
//                Log.i("tag", "action down -- x,y:" + x + "," + downY);
                if (extendPointRect != null && extendPointRect.contains(x, downY)) {
                    type = TYPE_EXTEND;
                    return true;
                }
                if (bookRect.contains(x, downY)) {
                    type = TYPE_MOVE;
                    return true;
                }
                if (bookCount == 0 && checkClick(downY)) {
                    type = TYPE_CLICK;
                    return true;
                }
                return false;
            case MotionEvent.ACTION_MOVE:
                float currentY = event.getY();
//                Log.i("tag", "action move -- y:" + currentY);
                float dY = currentY - downY;
                ScrollView parent = (ScrollView) getParent();
                parent.scrollBy(0, (int) dY / 2);
                switch (type) {
                    case TYPE_MOVE:
                        bookRect.set(bookRect.left, bookRect.top + dY, bookRect.right, bookRect.bottom + dY);
                        bookStart = Math.round((bookRect.top - paddingTop) / space);
                        //边缘修正
                        if (bookRect.top < paddingTop) {
                            bookStart = 0;
                            setBookRect(bookStart, bookCount);
                        }
                        if (bookRect.bottom > bottom) {
                            bookStart = titles.length - 1 - bookCount;
                            setBookRect(bookStart, bookCount);
                        }
                        break;
                    case TYPE_EXTEND:
                        bookRect.set(bookRect.left, bookRect.top, bookRect.right, bookRect.bottom + dY);
                        int end = (int) ((bookRect.bottom - paddingTop) / space);
                        bookCount = end - bookStart;
                        if (bookCount < 1) {
                            bookCount = 1;
                            setBookRect(bookStart, bookCount);
                        }
                        if (bookRect.bottom > bottom) {
                            end = titles.length - 1;
                            bookCount = end - bookStart;
                            setBookRect(bookStart, bookCount);
                        }
                        break;
                    case TYPE_CLICK:
                        break;
                }
                downY = currentY;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
//                Log.i("tag", "action up --");
                switch (type) {
                    case TYPE_MOVE:
                        if (bookRect.top < paddingTop) {
                            bookStart = 0;
                        }
                        break;
                    case TYPE_EXTEND:
                        int end = Math.round((bookRect.bottom - paddingTop) / space);
                        if (bookRect.bottom > bottom) {
                            end = titles.length - 1;
                        }
                        bookCount = end - bookStart;
                        break;
                    case TYPE_CLICK:
                        bookStart = (int) ((downY - paddingTop) / space);
                        bookCount = 2;
                        break;
                }
                setBookRect(bookStart, bookCount);
                postInvalidate();
                break;
        }
        return false;
    }

    private boolean checkClick(float y) {
        for (RectF rectF : usedAreas) {
            if (y >= rectF.top && y <= rectF.bottom) {
                return false;
            }
        }
        return true;
    }

    private void setBookRect(int start, int count) {
        bookRect.set(bookRect.left, paddingTop + space * start
                , bookRect.right, paddingTop + space * (start + count));
    }

}

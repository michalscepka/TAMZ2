package com.vsb.kru13.sokoban;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by kru13 on 12.10.16.
 */
public class SokoView extends View{

    Bitmap[] bmp;

    int lx = 10;
    int ly = 10;

    int width;
    int height;

    int heroX = 6;
    int heroY = 4;

    private float xT1, xT2, yT1, yT2;
    static final int MIN_DISTANCE = 150;

    private int level[] = {
            1,1,1,1,1,1,1,1,1,0,
            1,0,0,0,0,0,0,0,1,0,
            1,0,2,3,3,2,1,0,1,0,
            1,0,1,3,2,3,2,0,1,0,
            1,0,2,3,3,2,4,0,1,0,
            1,0,1,3,2,3,2,0,1,0,
            1,0,2,3,3,2,1,0,1,0,
            1,0,0,0,0,0,0,0,1,0,
            1,1,1,1,1,1,1,1,1,0,
            0,0,0,0,0,0,0,0,0,0
    };

    public SokoView(Context context) {
        super(context);
        init(context);
    }

    public SokoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SokoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        bmp = new Bitmap[6];

        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.box);
        bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.goal);
        bmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
        bmp[5] = BitmapFactory.decodeResource(getResources(), R.drawable.boxok);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / ly;
        height = h / lx;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Toast.makeText(getContext(), "Down", Toast.LENGTH_SHORT).show();
                xT1 = event.getX();
                yT1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                //Toast.makeText(getContext(), "Up", Toast.LENGTH_SHORT).show();
                xT2 = event.getX();
                yT2 = event.getY();

                float deltaX = xT2 - xT1;
                float deltaY = yT2 - yT1;

                if (Math.abs(deltaX) > MIN_DISTANCE && xT1 < xT2)
                {
                    //Toast.makeText(getContext(), "right swipe", Toast.LENGTH_SHORT).show();
                    move(1, 2, true);
                }
                else if (Math.abs(deltaX) > MIN_DISTANCE && xT1 > xT2)
                {
                    //Toast.makeText(getContext(), "left swipe", Toast.LENGTH_SHORT).show();
                    move(-1, -2, true);
                }

                if (Math.abs(deltaY) > MIN_DISTANCE && yT1 < yT2)
                {
                    //Toast.makeText(getContext(), "down swipe", Toast.LENGTH_SHORT).show();
                    move(10, 20, false);
                }
                else if (Math.abs(deltaY) > MIN_DISTANCE && yT1 > yT2)
                {
                    //Toast.makeText(getContext(), "up swipe", Toast.LENGTH_SHORT).show();
                    move(-10, -20, false);
                }
                break;
            }

        return true;
    }

    private void move(int pos1, int pos2, boolean horizontal) {
        if(level[heroY * 10 + heroX + pos1] == 1)
            return;

        if(level[heroY * 10 + heroX + pos1] == 2 && level[heroY * 10 + heroX + pos2] == 2)
            return;

        if(level[heroY * 10 + heroX + pos1] == 2 && level[heroY * 10 + heroX + pos2] == 1)
            return;

        if(level[heroY * 10 + heroX + pos1] == 2) {
            level[heroY * 10 + heroX] = 0;
            level[heroY * 10 + heroX + pos1] = 4;
            level[heroY * 10 + heroX + pos2] = 2;
        }
        else {
            level[heroY * 10 + heroX] = 0;
            level[heroY * 10 + heroX + pos1] = 4;
        }

        if(horizontal)
            heroX += pos1;
        else if(pos1 < 0)
            heroY--;
        else if(pos1 > 0)
            heroY++;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                canvas.drawBitmap(bmp[level[i*10 + j]], null,
                        new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
            }
        }

    }
}

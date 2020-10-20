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
            case MotionEvent.ACTION_DOWN: {
                float xT = event.getX();
                float yT = event.getY();
                if(xT > 800) {
                    move(1, 2, true);
                }
                if(xT < 400) {
                    move(-1, -2, true);
                }
                if(yT < 400) {
                    move(-10, -20, false);
                }
                if(yT > 1300) {
                    move(10, 20, false);
                }
                //Toast.makeText(getContext(), "x: " + xT +"; y: " + yT, Toast.LENGTH_SHORT).show();
                break;
            }
        }

        return super.onTouchEvent(event);
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

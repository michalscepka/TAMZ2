package com.vsb.kru13.sokoban;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SokoView extends View{

    Bitmap[] bmp;

    int lx = 10;
    int ly = 10;

    int width;
    int height;

    int heroX = 6;
    int heroY = 4;

    Queue<Integer> markPositions = new LinkedList<>();

    private float xT1;
    private float yT1;
    static final int MIN_DISTANCE = 150;

    private int levelIndex = 0;
    private int[] level;
    private List<Level> levels;

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

    public void setLevel(int[] newLevel, int levelHeight, int levelWidth, int spawnX, int spawnY) {
        level = newLevel.clone();
        lx = levelHeight;
        ly = levelWidth;
        heroX = spawnX;
        heroY = spawnY;
        adjustSize(getWidth(), getHeight());
        invalidate();
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public void setNextLevel() {
        if(!setNextLevel(++levelIndex))
            levelIndex--;
    }

    public boolean setNextLevel(int nextLevelId) {
        if(nextLevelId >= levels.size() || nextLevelId <= 0) {
            Toast.makeText(getContext(), "No more levels", Toast.LENGTH_LONG).show();
            return false;
        }

        Level l = levels.get(nextLevelId);
        level = l.layout.clone();
        lx = l.height;
        ly = l.width;
        heroX = l.spawnX;
        heroY = l.spawnY;
        adjustSize(getWidth(), getHeight());
        invalidate();
        return true;
    }

    public void setPreviousLevel() {
        if(!setNextLevel(--levelIndex))
            levelIndex++;
    }

    public void restartLevel() {
        setNextLevel(levelIndex);
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
        //width = w / ly;
        //height = h / lx;
        adjustSize(w, h);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void adjustSize(int w, int h) {
        width = w / ly;
        height = h / lx;
        Log.d("size", "orgWidth = " + width);
        Log.d("size", "orgHeight = " + height);

        while(height > width * 2) {
            height /= 2;
            Log.d("size", "newHeight = " + height);
        }
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
                float xT2 = event.getX();
                float yT2 = event.getY();

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
                    move(ly, ly*2, false);
                }
                else if (Math.abs(deltaY) > MIN_DISTANCE && yT1 > yT2)
                {
                    //Toast.makeText(getContext(), "up swipe", Toast.LENGTH_SHORT).show();
                    move(-ly, -ly*2, false);
                }
                break;
            }

        return true;
    }

    private void move(int pos1, int pos2, boolean horizontal) {
        if(level[heroY * ly + heroX + pos1] == 1 ||
            level[heroY * ly + heroX + pos1] == 2 && level[heroY * ly + heroX + pos2] == 2 ||
            level[heroY * ly + heroX + pos1] == 5 && level[heroY * ly + heroX + pos2] == 5 ||
            level[heroY * ly + heroX + pos1] == 2 && level[heroY * ly + heroX + pos2] == 5 ||
            level[heroY * ly + heroX + pos1] == 5 && level[heroY * ly + heroX + pos2] == 2 ||
            level[heroY * ly + heroX + pos1] == 2 && level[heroY * ly + heroX + pos2] == 1 ||
            level[heroY * ly + heroX + pos1] == 5 && level[heroY * ly + heroX + pos2] == 1)
            return;

        //posouvani beden
        if(level[heroY * ly + heroX + pos1] == 2 || level[heroY * ly + heroX + pos1] == 5) {
            //kontrola krizku na zemi
            if(level[heroY * ly + heroX + pos2] == 3) {
                level[heroY * ly + heroX + pos2] = 5;
            }
            else {
                level[heroY * ly + heroX + pos2] = 2;
            }
            //pokud byla posunuta zelena bedna, zapamatovat si kam vratit krizek
            if(level[heroY * ly + heroX + pos1] == 5) {
                markPositions.add(heroY * ly + heroX + pos1);
            }
        }
        //kdyz slapne hrac na krizek
        else if(level[heroY * ly + heroX + pos1] == 3) {
            markPositions.add(heroY * ly + heroX + pos1);
        }

        //posunout hrace
        level[heroY * ly + heroX] = 0;
        level[heroY * ly + heroX + pos1] = 4;

        //vratit krizek
        if(!markPositions.isEmpty()) {
            if(markPositions.element() == heroY * ly + heroX) {
                level[markPositions.remove()] = 3;
            }
        }

        if(horizontal)
            heroX += pos1;
        else if(pos1 < 0)
            heroY--;
        else if(pos1 > 0)
            heroY++;

        invalidate();

        if(isWin()) {
            Toast.makeText(getContext(), "Win", Toast.LENGTH_LONG).show();
            setNextLevel();
        }
    }

    private boolean isWin() {
        for(int item : level) {
            if (item == 3 || item == 2)
                return false;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                canvas.drawBitmap(bmp[level[i*ly + j]], null,
                        new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
            }
        }
    }
}

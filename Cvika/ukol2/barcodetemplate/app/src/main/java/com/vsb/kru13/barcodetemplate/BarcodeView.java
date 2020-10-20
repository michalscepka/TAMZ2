package com.vsb.kru13.barcodetemplate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BarcodeView extends View {

    //UPC-A code

    //http://en.wikipedia.org/wiki/EAN_code
    //http://www.terryburton.co.uk/barcodewriter/generator/

    static final int[][] NUMBERS = {
            new int[] {0, 0, 0, 1, 1, 0, 1},
            new int[] {0, 0, 1, 1, 0, 0, 1},
            new int[] {0, 0, 1, 0, 0, 1, 1},
            new int[] {0, 1, 1, 1, 1, 0, 1},
            new int[] {0, 1, 0, 0, 0, 1, 1},
            new int[] {0, 1, 1, 0, 0, 0, 1},
            new int[] {0, 1, 0, 1, 1, 1, 1},
            new int[] {0, 1, 1, 1, 0, 1, 1},
            new int[] {0, 1, 1, 0, 1, 1, 1},
            new int[] {0, 0, 0, 1, 0, 1, 1}
    };

    final static int BARCODE_WIDTH =  600;
    final static int BARCODE_HEIGHT = 200;
    final static int BARCODE_LINE_WIDTH = 5;

    // čísla čárového kódu
    int[] code = new int[12];

    private final Paint twPaint = new Paint();
    private final Paint tbPaint = new Paint();
    private final Paint trPaint = new Paint();
    private int posX = 0;

    public BarcodeView(Context context) {
        super(context);
        setDefaults();
    }

    public BarcodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setDefaults();
    }

    public BarcodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDefaults();
    }

    public BarcodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setDefaults();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // při změně velikosti view,  w a h obsahují novou velikost
    }

    // nastaví výchozí hodnoty
    void setDefaults() {
        int[] copyFrom = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2};
        System.arraycopy(copyFrom, 0, code, 0, copyFrom.length);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        twPaint.setColor(Color.WHITE);
        tbPaint.setColor(Color.BLACK);
        trPaint.setColor(Color.RED);
        trPaint.setStrokeWidth(BARCODE_LINE_WIDTH);

        // vykreslí bílý obdelník do kterého se bude kreslit čárový kód
        canvas.drawRect(new Rect(0, 0, BARCODE_WIDTH, BARCODE_HEIGHT), twPaint);

        // tloušťka čáry
        tbPaint.setStrokeWidth(BARCODE_LINE_WIDTH);

        // velikost písma, antialiasing
        tbPaint.setTextSize(30);
        tbPaint.setAntiAlias(true);


        paintStartEnd(canvas);

        for(int i = 0; i < code.length/2; i++) {
            for(int j = 0; j < 7; j++) {
                if(NUMBERS[code[i]][j] == 0) {
                    canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, twPaint);
                }
                else {
                    canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, tbPaint);
                }
                posX += BARCODE_LINE_WIDTH;
            }
        }

        paintMiddle(canvas);

        for(int i = code.length/2; i < code.length; i++) {
            for(int j = 0; j < 7; j++) {
                if(NUMBERS[code[i]][j] == 1) {
                    canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, twPaint);
                }
                else {
                    canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, tbPaint);
                }
                posX += BARCODE_LINE_WIDTH;
            }
        }

        paintStartEnd(canvas);

        StringBuilder sb = new StringBuilder();
        for (int value : code) {
            sb.append(value).append("   ");
        }

        canvas.drawText(sb.toString(), (10), (BARCODE_HEIGHT + 30), tbPaint);
    }

    private void paintStartEnd(Canvas canvas) {
        canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, trPaint);
        posX += BARCODE_LINE_WIDTH;
        canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, twPaint);
        posX += BARCODE_LINE_WIDTH;
        canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, trPaint);
        posX += BARCODE_LINE_WIDTH;
    }

    private void paintMiddle(Canvas canvas) {
        canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, twPaint);
        posX += BARCODE_LINE_WIDTH;
        canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, trPaint);
        posX += BARCODE_LINE_WIDTH;
        canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, twPaint);
        posX += BARCODE_LINE_WIDTH;
        canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, trPaint);
        posX += BARCODE_LINE_WIDTH;
        canvas.drawLine(posX, 0, posX, BARCODE_HEIGHT, twPaint);
        posX += BARCODE_LINE_WIDTH;
    }
}

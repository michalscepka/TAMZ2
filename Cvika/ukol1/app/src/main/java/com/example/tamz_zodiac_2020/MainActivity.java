package com.example.tamz_zodiac_2020;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;

public class MainActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener{

    DatePicker myDate;
    TextView myText;
    TextView myText2;
    ImageView myImage;
    int globalMonth;
    int globalDay;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    static int zodiacSymbols[] = {R.drawable.kozoroh01, R.drawable.vodnar02, R.drawable.ryby03, R.drawable.beran04, R.drawable.byk05, R.drawable.blizenci06, R.drawable.rak07, R.drawable.lev08, R.drawable.panna09, R.drawable.vahy10, R.drawable.stir11, R.drawable.strelec12};
    static String zodiacNames[] = {"Kozoroh", "Vodnář", "Ryby", "Beran", "Býk", "Blíženci", "Rak", "Lev", "Panna", "Váhy", "Štír", "Střelec"};
    static int breakDate[] = {21, 21, 21, 21, 22, 22, 23, 23, 23, 24, 23, 23};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        int year = sharedPref.getInt("year", 2000);
        int month = sharedPref.getInt("month", 2);
        int day = sharedPref.getInt("day", 15);

        myDate = findViewById(R.id.myDatePicker);
        myDate.init(year, month, day, this);

        myText = findViewById(R.id.textView);
        myText.setText("Zadej Datum");

        myImage = findViewById(R.id.imageView);
        myText2 = findViewById(R.id.textView2);

        onDateChanged(myDate, year, month, day);

        setMyTheme();
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        Log.d("mesic ", " " + monthOfYear);
        globalMonth = monthOfYear;
        globalDay = dayOfMonth;

        if(globalDay < breakDate[globalMonth]) {
            myImage.setImageResource(zodiacSymbols[globalMonth]);
            myText2.setText(zodiacNames[globalMonth]);
        }
        else {
            myImage.setImageResource(zodiacSymbols[(globalMonth + 1) % 12]);
            myText2.setText(zodiacNames[(globalMonth + 1) % 12]);
        }
    }

    public void zImageClick(View view) {
        myImage = findViewById(R.id.imageView);
        myImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v == myImage) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            //overlay
                            myImage.getDrawable().setColorFilter(Color.argb(100, 0, 255, 0), PorterDuff.Mode.SRC_ATOP);
                            myImage.invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            //clear the overlay
                            myImage.getDrawable().clearColorFilter();
                            myImage.invalidate();

                            Intent browserIntent;
                            if(globalDay < breakDate[globalMonth]) {
                                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.horoskopy.cz/" + stripAccents(zodiacNames[globalMonth])));
                            }
                            else {
                                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.horoskopy.cz/" + stripAccents(zodiacNames[(globalMonth + 1) % 12])));
                            }
                            startActivity(browserIntent);
                        }
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.info:
                startActivity(new Intent(this, InfoActivity.class));
                return true;
            case R.id.settings:
                startActivityForResult(new Intent(this, SettingsActivity.class), 123);
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check if the request code is same as what is passed
        if(requestCode == 123) {
            int result = data.getIntExtra("result", 0);

            editor = sharedPref.edit();
            editor.putInt("theme", result);
            editor.apply();

            setMyTheme();
        }
    }

    private void setMyTheme() {
        ConstraintLayout layout = findViewById(R.id.layout);

        if(sharedPref.getInt("theme", 0) == 1) {
            layout.setBackgroundResource(R.drawable.wall01);
            myText.setTextColor(Color.WHITE);
            myText2.setTextColor(Color.WHITE);
        }
        if(sharedPref.getInt("theme", 0) == 2) {
            layout.setBackgroundResource(R.drawable.wall02);
            myText.setTextColor(Color.WHITE);
            myText2.setTextColor(Color.WHITE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        editor = sharedPref.edit();
        editor.putInt("day", myDate.getDayOfMonth());
        editor.putInt("month", myDate.getMonth());
        editor.putInt("year", myDate.getYear());
        editor.apply();
    }

    private String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}

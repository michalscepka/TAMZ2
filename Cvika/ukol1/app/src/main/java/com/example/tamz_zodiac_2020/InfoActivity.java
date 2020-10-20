package com.example.tamz_zodiac_2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView myText1;
    TextView myText2;
    TextView myText3;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        myText1 = findViewById(R.id.textViewAppName);
        myText2 = findViewById(R.id.textViewAppLogin);
        myText3 = findViewById(R.id.textViewAppVersion);
        myText1.setText("TAMZ Zodiac");
        myText2.setText("Autor: SCE0007 - 2020");
        myText3.setText("verze: 1.0.0");

        sharedPref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        setMyTheme();
    }

    private void setMyTheme() {
        ConstraintLayout layout = findViewById(R.id.layout);

        if(sharedPref.getInt("theme", 0) == 1) {
            layout.setBackgroundResource(R.drawable.wall01);
            myText1.setTextColor(Color.WHITE);
            myText2.setTextColor(Color.WHITE);
            myText3.setTextColor(Color.WHITE);
        }
        if(sharedPref.getInt("theme", 0) == 2) {
            layout.setBackgroundResource(R.drawable.wall02);
            myText1.setTextColor(Color.WHITE);
            myText2.setTextColor(Color.WHITE);
            myText3.setTextColor(Color.WHITE);
        }
    }
}

package com.example.tamz_zodiac_2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    Button button1;
    Button button2;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        sharedPref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        setMyTheme();
    }

    public void onButton(View view) {
        Intent intent = new Intent();
        if(view == button1) {
            intent.putExtra("result", 1);
            setResult(123, intent);
            finish();
        }
        if(view == button2) {
            intent.putExtra("result", 2);
            setResult(123, intent);
            finish();
        }
    }

    private void setMyTheme() {
        ConstraintLayout layout = findViewById(R.id.layout);

        if(sharedPref.getInt("theme", 0) == 1) {
            layout.setBackgroundResource(R.drawable.wall01);
        }
        if(sharedPref.getInt("theme", 0) == 2) {
            layout.setBackgroundResource(R.drawable.wall02);
        }
    }
}

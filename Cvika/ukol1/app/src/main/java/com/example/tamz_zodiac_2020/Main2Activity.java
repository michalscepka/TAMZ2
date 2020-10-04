package com.example.tamz_zodiac_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myText = findViewById(R.id.textView);

        Intent intent = getIntent();
        Integer month = intent.getIntExtra("monthOfYear", -1);
        Toast.makeText(this, "monthOfYear: " + month, Toast.LENGTH_SHORT).show();
        myText.setText("monthOfYear: " + month);

    }
}

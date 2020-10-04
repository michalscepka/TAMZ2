package com.example.cv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myText = findViewById(R.id.textView2);

        Intent myIntent = getIntent();
        int a = myIntent.getIntExtra("vysledek", -1);
        myText.setText("Prijata data: " + a);
        Toast.makeText(this, "Prijata data: " + a, Toast.LENGTH_LONG).show();
    }
}

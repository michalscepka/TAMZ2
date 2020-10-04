package com.example.tamz_zodiac_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener{

    DatePicker myDate;
    TextView myText;
    TextView myText2;
    ImageView myImage;
    int globalMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDate = findViewById(R.id.myDatePicker);
        myDate.init(2015, 0, 1, this);

        myText = findViewById(R.id.textView);
        myText.setText("Zadej Datum");

        myImage = findViewById(R.id.imageView);

        myText2 = findViewById(R.id.textView2);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth){

        Log.d("mesic ", " " + monthOfYear);
        globalMonth = monthOfYear;
        myImage.setImageResource(R.drawable.beran04);
        myText2.setText("Beran");
    }

    public void zImageClick(View view) {
        Toast.makeText(this, "zImageClick", Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(this, Main2Activity.class);
        myIntent.putExtra("monthOfYear", globalMonth);
        startActivity(myIntent);

    }
}

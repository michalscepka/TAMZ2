package com.example.cv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = findViewById(R.id.textView);
    }

    public void onButtonClick(View view) {
        int a = 5 + 5;
        //Toast.makeText(this, "HELLO: " + a, Toast.LENGTH_LONG).show();
        Log.d("onButtonClick", "jsem ve funkci onButton: " + a);
        myText.setText("Vysledek: " + a);

        Intent myIntent = new Intent(this, MainActivity2.class);
        myIntent.putExtra("vysledek", a);
        startActivity(myIntent);
    }
}

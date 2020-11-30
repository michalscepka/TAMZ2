package com.example.netactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ConversionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        ImageView imgFlagOther = findViewById(R.id.imgFlag);
        final TextView czkEditText = findViewById(R.id.editTextCZK);
        final TextView otherEditText = findViewById(R.id.editTextOther);
        TextView otherText = findViewById(R.id.textOther);
        TextView otherZemeText = findViewById(R.id.textOtherZeme);

        Intent intent = getIntent();
        final Entry entry = (Entry) intent.getSerializableExtra("entry");
        //Toast.makeText(this, entry.zeme, Toast.LENGTH_LONG).show();

        imgFlagOther.setImageResource(getResources().getIdentifier("flag_" + entry.kod.toLowerCase(), "drawable", getPackageName()));
        otherText.setText(entry.kod);
        otherZemeText.setText(String.format("%s\n1 CZK = %s %s", entry.zeme, entry.kurz, entry.kod));

        final DecimalFormat f = new DecimalFormat("#0.00");

        czkEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(czkEditText.hasFocus()) {
                    try {
                        Double input = Double.parseDouble(s.toString());
                        Double kurz = Double.parseDouble(entry.kurz.replace(',', '.'));
                        otherEditText.setText(f.format(input / kurz));
                    } catch (Exception e) {
                        otherEditText.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        otherEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(otherEditText.hasFocus()) {
                    try {
                        Double input = Double.parseDouble(s.toString());
                        Double kurz = Double.parseDouble(entry.kurz.replace(',', '.'));
                        czkEditText.setText(f.format(input * kurz));
                    } catch (Exception e) {
                        czkEditText.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), NetworkActivity.class);
        startActivity(intent);
    }
}

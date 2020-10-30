package com.vsb.kru13.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    SokoView sokoView;
    String[] levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sokoView = findViewById(R.id.sokoView);

        AssetManager assetManager = getAssets();
        InputStream input;
        try {
            input = assetManager.open("levels.txt");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String text = new String(buffer);
            //Log.d("level ", text);

            levels = text.split("\\bLevel \\b\\d*\\R['\\w* \\d]*");
            for(String s : levels)
                Log.d("levels", s);

            prepareLevel(3);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareLevel(int id) {
        String[] level = levels[id].split("\\R");

        //zjisteni nejdelsiho radku
        int maxRowLength = 0;
        for(String s : level)
            if(maxRowLength < s.length())
                maxRowLength = s.length();

        Log.d("length ", String.valueOf(maxRowLength));

        //zarovnani radku a replace znaku
        for(int i = 0; i < level.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(level[i].replace(' ', '0')
                    .replace('#', '1')
                    .replace('$', '2')
                    .replace('.', '3')
                    .replace('@', '4')
                    .replace('*', '5'));
            for(int j = sb.length(); j < maxRowLength; j++)
                sb.append("0");
            level[i] = sb.toString();
            Log.d("level ", level[i]);
        }

        //string to int
        int[] newLevel = new int[maxRowLength * level.length];
        int spawnX = 0;
        int spawnY = 0;
        int counter = 0;
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[i].length(); j++) {
                newLevel[counter] = Character.getNumericValue(level[i].charAt(j));
                if(newLevel[counter] == 4) {
                    spawnX = j;
                    spawnY = i;
                }
                counter++;
            }
        }

        Log.d("level ", Arrays.toString(newLevel));
        sokoView.setLevel(newLevel, level.length, maxRowLength, spawnX, spawnY);
    }
}

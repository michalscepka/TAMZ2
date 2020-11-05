package com.example.tamz2_sqlite;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBHelper mydb;
    ListView itemListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList = mydb.getItemList();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);

        itemListView = findViewById(R.id.listView1);
        itemListView.setAdapter(arrayAdapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO 2: zavolat aktivitu (DisplayItemActivity), ktera bude zobrazovat informace o zaznamu v db a predat ji hledane id zaznamu
                //TODO 2: pokud je id > 0, zobrazi informace o danem zaznamu
            }
        });

    }

    @Override
    public void onResume()
    {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_menu_item)
        {
            //TODO 1: v menu (pokud je zvoleno pridani noveho zaznamu - Add Item) zavolat aktivitu, ktera obslouzi pridani zaznamu (DisplayItemActivity)
            //TODO 1: do aktivity DisplayRecordActivity se posila id;
            //TODO 1: pokud je id = 0, vytvari se novy zaznam
        }

        if (id == R.id.del_menu_item)
        {
            //TODO 5: zde se vola metoda pro odstraneni vsech zaznamu
            int nRowDeleted = mydb.removeAll();
            Toast.makeText(this, "Deleted records: " + nRowDeleted, Toast.LENGTH_SHORT).show();
            recreate();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();
    }
}

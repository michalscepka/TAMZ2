package com.example.tamz2_sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayItemActivity extends AppCompatActivity {

    private DBHelper mydb;
    TextView nameTextView;
    int idToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_record);

        nameTextView = findViewById(R.id.editTextName);
        mydb = new DBHelper(this);
        Intent i = getIntent();
        if(i !=null)
        {
            //ziskam ID, ktere se ma editovat/zobrazit/mazat - poslane z hlavni aktivity
            int value = i.getIntExtra("id", 0);
            idToUpdate = value;
            if (idToUpdate > 0)
            {
                //z db vytahnu zaznam pod hledanym ID a ulozim do idToUpdate
                Cursor rs = mydb.getData(idToUpdate);
                rs.moveToFirst();

                //z DB vytahnu jmeno zaznamu
                String nameDB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_NAME));

                if (!rs.isClosed())
                {
                    rs.close();
                }
                Button b = findViewById(R.id.buttonSave);
                b.setVisibility(View.INVISIBLE);

                nameTextView.setText(nameDB);
                nameTextView.setEnabled(false);
                nameTextView.setFocusable(false);
                nameTextView.setClickable(false);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(idToUpdate>0){
            getMenuInflater().inflate(R.menu.display_record_menu, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Edit_Contact) {
            Button b = findViewById(R.id.buttonSave);
            b.setVisibility(View.VISIBLE);

            nameTextView.setEnabled(true);
            nameTextView.setFocusableInTouchMode(true);
            nameTextView.setClickable(true);

        }
        if (id == R.id.Delete_Contact)
        {
            //TODO 3: zavolat z mydb metodu na odstraneni zaznamu
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        return true;
    }

    public void saveButtonAction(View view)
    {

        if(idToUpdate > 0){
            //TODO 4: zavolat z mydb metodu na update zaznamu
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else{
            //vlozeni zaznamu
            if(mydb.insertItem(nameTextView.getText().toString())){
                Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
            }

            else{
                Toast.makeText(getApplicationContext(), "not saved", Toast.LENGTH_SHORT).show();
            }
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
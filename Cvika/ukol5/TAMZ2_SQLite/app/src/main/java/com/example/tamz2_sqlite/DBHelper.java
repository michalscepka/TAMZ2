package com.example.tamz2_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "DBTAMZ2.db";
    public static final String ITEM_COLUMN_ID = "id";
    public static final String ITEM_COLUMN_NAME = "name";
    public static final String ITEM_COLUMN_COST = "cost";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE items " + "(id INTEGER PRIMARY KEY, name TEXT, cost INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }

    public boolean insertItem(String name, String cost)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        try {
            contentValues.put("cost", Integer.parseInt(cost));
        }catch (NumberFormatException e) { return false; }
        long insertedId = db.insert("items", null, contentValues);
        if (insertedId == -1) return false;
        return true;
    }

    public boolean deleteItem (int id)
    {
        //TODO 3: doplnit kod pro odstraneni zaznamu
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.execSQL("DELETE FROM items WHERE id=" + id + "");
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    //Cursor representuje vracena data
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from items where id=" + id + "", null);
    }

    public boolean updateItem (Integer id, String name, String cost)
    {
        //TODO 4: doplnit kod pro update zaznamu
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.execSQL("UPDATE items SET name ='" + name + "', cost =" + cost + " WHERE id=" + id + "");
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    public ArrayList<Item> getItemList()
    {
        ArrayList<Item> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from items", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            //TODO 6: doplnit kod i pro další sloupce tabulky (například ITEM_COLUMN_COST)
            String name = res.getString(res.getColumnIndex(ITEM_COLUMN_NAME));
            int id = res.getInt(res.getColumnIndex(ITEM_COLUMN_ID));
            int cost = res.getInt(res.getColumnIndex(ITEM_COLUMN_COST));
            arrayList.add(new Item(id, name, cost));
            res.moveToNext();
        }

        return arrayList;
    }

    public int removeAll()
    {
        //TODO 5: doplnit kod pro odstraneni vsech zaznamu
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM items", null);
        c.moveToFirst();

        db.execSQL("DELETE FROM items");

        return c.getInt(0);
    }
}

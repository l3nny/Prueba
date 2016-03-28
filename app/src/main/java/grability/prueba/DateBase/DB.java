package grability.prueba.DateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import grability.prueba.Reader.Feader;

public class DB {

    private SQLiteDatabase DB;
    private SQLiteHelper dbHelper;


    public DB(Context context) {
        dbHelper = SQLiteHelper.getInstance(context);
    }

    public void open() {
        DB = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertApps(String w, String d, String f, String h, byte[] i) {

        try {
            ContentValues cv = new ContentValues();
            cv.put("name", w);
            cv.put("category", d);
            cv.put("summary", f);
            cv.put("urlImage", h);
            cv.put("image", i);
            DB.insert("apps", null, cv);


        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }


    public void deleteAll() {
        try {
            DB.delete("apps", null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public boolean emptyTable() {
        boolean r = false;
        Cursor result = DB.rawQuery("SELECT COUNT(*) FROM apps", null);
        if (result != null) {
            result.moveToFirst();
            if (result.getInt(0) == 0) {
                r = true;
            } else {
                r = false;
            }
        }
        return r;
    }


    public Feader[] listCategory() {


        Cursor cursor = DB.rawQuery("SELECT category FROM apps GROUP BY category HAVING (COUNT(*) >= 1)", null);

        List<Feader> list = new ArrayList<Feader>();


        if (cursor.moveToFirst()) {
            do {
                list.add(new Feader(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        Feader apps[] = new Feader[list.size()];
        try {
            for (int i = 0; i < list.size(); i++) {

                apps[i] = ((Feader) list.get(i));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apps;

    }

    public ArrayList<Feader> ListaIMG(String Category) {
        Feader f = new Feader();
        ArrayList<Feader> ff = new ArrayList<Feader>();

        try {
            Cursor cursor = DB.rawQuery("SELECT name, urlImage, image FROM apps WHERE category ='" + Category + "'", null);

            if (cursor.moveToFirst()) {
                do {

                    f.setName(cursor.getString(0));
                    f.setURLimage(cursor.getString(1));
                    f.setImage(cursor.getBlob(2));

                    ff.add(f);
                    f = new Feader();


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ff;
    }


    public ArrayList<Feader> information(String info) {

        Cursor cursor = DB.rawQuery("SELECT name, summary FROM apps WHERE urlImage ='" + info + "'", null);

        ArrayList<Feader> list = new ArrayList<Feader>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new Feader(cursor.getString(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

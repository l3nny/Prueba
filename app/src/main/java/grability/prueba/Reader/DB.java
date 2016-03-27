package grability.prueba.Reader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

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


    public void eliminarTodo() {
        try {
            DB.execSQL("DELETE FROM \"apps\"");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public boolean TablaVacia() {
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


    public Feader[] ListaCategorias() {


        Cursor cursor = DB.rawQuery("SELECT category FROM apps GROUP BY category HAVING (COUNT(*) >= 1)", null);

        List<Feader> lista = new ArrayList<Feader>();


        if (cursor.moveToFirst()) {
            do {
                lista.add(new Feader(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        Feader apps[] = new Feader[lista.size()];
        try {
            for (int i = 0; i < lista.size(); i++) {

                apps[i] = ((Feader) lista.get(i));

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
            Cursor cursor = DB.rawQuery("SELECT urlImage, image FROM apps WHERE category ='" + Category + "'", null);

            if (cursor.moveToFirst()) {
                do {


                    f.setURLimage(cursor.getString(0));
                    f.setImage(cursor.getBlob(1));

                    ff.add(f);
                    f = new Feader();


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ff;
    }


    public ArrayList<Feader> informacion(String info) {

        Cursor cursor = DB.rawQuery("SELECT name, summary FROM apps WHERE urlImage ='" + info + "'", null);

        ArrayList<Feader> lista = new ArrayList<Feader>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    lista.add(new Feader(cursor.getString(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

}

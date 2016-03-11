package Reader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

public class DB {

    private SQLiteDatabase DB;
    private SQLiteHelper dbHelper;
    Feader f;


    public DB(Context context) {
        dbHelper = SQLiteHelper.getInstance(context);
    }

    public void open() {
        DB = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertApps(String w, String d, String f, String h) {

        try {

            DB.execSQL("INSERT INTO \"apps\" (\"name\", \"category\", \"summary\", \"urlImage\") VALUES ('" + w + "', '" + d + "','" + f + "','" + h + "')");
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

    public ArrayList<String> ListaIMG(String Category) {


        Cursor cursor = DB.rawQuery("SELECT urlImage FROM apps WHERE category ='" + Category + "'", null);

        ArrayList<String> lista = new ArrayList<String>();


        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return lista;

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

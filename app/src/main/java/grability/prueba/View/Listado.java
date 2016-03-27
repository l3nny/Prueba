package grability.prueba.View;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TableLayout;

import java.util.ArrayList;

import grability.prueba.R;
import grability.prueba.Reader.DB;
import grability.prueba.Reader.Feader;
import grability.prueba.Reader.Tabla;

public class Listado extends Activity {
    DB base;
    Tabla tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        if (getResources().getBoolean(R.bool.landscape_only) == true) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }


        tabla = new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        base = new DB(this);
        base.open();

        Bundle recieve = getIntent().getExtras();
        String categoria = recieve.getString("Category");

        ArrayList<Feader> stringList = base.ListaIMG(categoria);
        ArrayList<Bitmap> images = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();


        byte[] img;
        Bitmap bitmap;

        try {

            boolean par = false;
            int count = 0;
            for (int i = 0; i < stringList.size(); i++) {
                if (stringList.get(i) == null) {
                    break;
                }
                img = stringList.get(i).getImage();
                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
                images.add(bitmap);
                if(stringList.get(i).getName().length() > 20) {
                    names.add(stringList.get(i).getName().substring(0, 20));
                }else {
                    names.add(stringList.get(i).getName());
                }
                urls.add(stringList.get(i).getURLimage());
                count++;
                if (count == 3) {
                    tabla.agregarFilaTabla(images, urls);
                    tabla.agregarFilaTablabo(names);
                    images = new ArrayList<>();
                    urls = new ArrayList<>();
                    names = new ArrayList<>();
                    par = true;
                    count = 0;
                } else {
                    par = false;
                }
            }
            if (par == false) {
                tabla.agregarFilaTabla(images, urls );
                tabla.agregarFilaTablabo(names);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}




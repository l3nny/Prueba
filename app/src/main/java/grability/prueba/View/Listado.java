package grability.prueba.View;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TableLayout;

import java.util.ArrayList;

import grability.prueba.Reader.DB;
import grability.prueba.Reader.Feader;
import grability.prueba.Reader.Tabla;
import grability.prueba.R;

public class Listado extends Activity {
    DB base;
    Tabla tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        if(getResources().getBoolean(R.bool.landscape_only)== true){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }else{
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
        byte[] img;
        Bitmap bitmap;

        try {
            for(int i=0;i<stringList.size();i++){
                img = stringList.get(i).getImage();
                bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

                images.add(bitmap);
                urls.add(stringList.get(i).getURLimage());
            }

            tabla.agregarFilaTabla(images, urls);
            tabla.agregarFilaTablabo();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}




package grability.prueba;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TableLayout;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Reader.DB;
import Reader.Tabla;

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

        ArrayList<String> stringList = base.ListaIMG(categoria);


        LoadImage li = new LoadImage(stringList);
        li.execute();
        try {
            ArrayList<Bitmap> bm = li.get();

            tabla.agregarFilaTabla(bm, stringList);
            tabla.agregarFilaTablabo();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    class LoadImage extends AsyncTask<ArrayList<String>, Void, ArrayList<Bitmap>> {
        ArrayList<String> bmImage;

        public LoadImage(ArrayList<String> bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(ArrayList<String>... urls) {

            ArrayList<Bitmap> mIcon11 = new ArrayList<Bitmap>();
            InputStream is = null;
            Bitmap bm = null;
            try {
                for (int i = 0; i < bmImage.size(); i++) {
                    is = (InputStream) new URL(bmImage.get(i)).getContent();
                    bm = BitmapFactory.decodeStream(is);
                    mIcon11.add(bm);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> result) {
            super.onPostExecute(result);

        }
    }
}




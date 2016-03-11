package grability.prueba;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Reader.DB;
import Reader.Feader;
import Reader.Tabla;

public class Resumen extends Activity {

    DB base;
    Tabla t;
    ArrayList<Feader> f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        if(getResources().getBoolean(R.bool.landscape_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        base = new DB(this);
        base.open();


        Bundle recieve = getIntent().getExtras();
        String url = recieve.getString("url");
        Bitmap imagen = getIntent().getParcelableExtra("imagen");
        f = base.informacion(url);

        TextView titulo = (TextView) findViewById(R.id.titulo);
        TextView resumen = (TextView) findViewById(R.id.resumen);


        titulo.setText(f.get(0).getName().toString());
        resumen.setText(f.get(0).getSummary().toString());

        ImageView imageView = (ImageView) findViewById(R.id.imagen);
        imageView.setImageBitmap(redimensionarImagenMaximo(imagen,500,500));
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){

        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}

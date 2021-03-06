package grability.prueba.View;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import grability.prueba.DateBase.DB;
import grability.prueba.Reader.Feader;
import grability.prueba.Other.DynamicTable;
import grability.prueba.R;

public class Summary extends Activity {

    DB base;
    DynamicTable t;
    ArrayList<Feader> f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        if (getResources().getBoolean(R.bool.landscape_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        base = new DB(this);
        base.open();


        Bundle recieve = getIntent().getExtras();
        String url = recieve.getString("url");
        Bitmap image = getIntent().getParcelableExtra("image");
        f = base.information(url);

        TextView title = (TextView) findViewById(R.id.title);
        TextView summary = (TextView) findViewById(R.id.summary);


        title.setText(f.get(0).getName().toString());
        summary.setText(f.get(0).getSummary().toString());

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageBitmap(redimensionarImagenMaximo(image, 500, 500));
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth) {

        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}

package grability.prueba.Other;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import grability.prueba.R;
import grability.prueba.View.Summary;

public class DynamicTable {


    private TableLayout table;
    private ArrayList<TableRow> rowns;
    private Activity activity;
    private Resources rs;
    private int ROWS, COLUMNS;

    public DynamicTable(Activity activity, TableLayout table) {
        this.activity = activity;
        this.table = table;
        rs = this.activity.getResources();
        ROWS = COLUMNS = 0;
        rowns = new ArrayList<TableRow>();
    }

    public void addRowName(ArrayList<String> name) {
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow rown = new TableRow(activity);
        rown.setLayoutParams(layoutFila);

        for (int i = 0; i < name.size(); i++) {
            final TextView name1 = new TextView(activity);
            name1.setGravity(Gravity.CENTER);
            name1.setText(name.get(i));
            name1.setPadding(50, 20, 5, 20);
            rown.addView(name1);
        }


        table.addView(rown);
        rowns.add(rown);

        ROWS++;
    }

    public void addRowImage(final ArrayList<Bitmap> elementos, ArrayList<String> url) {
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow rown = new TableRow(activity);
        rown.setLayoutParams(layoutFila);

        int a = 0;


        for (int i = 0; i < elementos.size(); i++) {
            final ImageButton img = new ImageButton(activity);

            a++;

            final String Url = url.get(i);
            final Bitmap image = elementos.get(i);


            img.setId(a);
            final int id_ = img.getId();
            img.setImageBitmap(resizeImage(elementos.get(i), 300, 300));
            img.setBackgroundColor(Color.TRANSPARENT);
            img.setPadding(5, 20, 5, 20);
            rown.addView(img);
            img.setId(id_);

            img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Summary.class);
                    intent.putExtra("url", Url);
                    intent.putExtra("image", image);
                    view.getContext().startActivity(intent);
                    activity.overridePendingTransition(R.anim.derecha1, R.anim.derecha2);
                }
            });

            rowns.add(rown);
            ROWS++;

        }

        table.addView(rown);


    }

    public Bitmap resizeImage(Bitmap mBitmap, float newWidth, float newHeigth) {

        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}

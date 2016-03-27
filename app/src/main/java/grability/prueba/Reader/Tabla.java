package grability.prueba.Reader;

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
import grability.prueba.View.Resumen;

public class Tabla {


    private TableLayout tabla;
    private ArrayList<TableRow> filas;
    private Activity actividad;
    private Resources rs;
    private int FILAS, COLUMNAS;
    //DB base;

    public Tabla(Activity actividad, TableLayout tabla) {
        this.actividad = actividad;
        this.tabla = tabla;
        rs = this.actividad.getResources();
        FILAS = COLUMNAS = 0;
        filas = new ArrayList<TableRow>();
    }

    public void agregarFilaTablabo(ArrayList<String> name) {
        TableRow.LayoutParams layoutCelda;
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(actividad);
        fila.setLayoutParams(layoutFila);

        for (int i = 0; i < name.size(); i++) {
            final TextView name1 = new TextView(actividad);
            name1.setGravity(Gravity.CENTER);
            name1.setText(name.get(i));
            name1.setPadding(50, 20, 5, 20);
            fila.addView(name1);
        }


        tabla.addView(fila);
        filas.add(fila);

        FILAS++;
    }

    public void agregarFilaTabla(final ArrayList<Bitmap> elementos, ArrayList<String> url) {
        TableRow.LayoutParams layoutCelda;
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(actividad);
        fila.setLayoutParams(layoutFila);

        int a = 0;


        for (int i = 0; i < elementos.size(); i++) {
            final ImageButton img = new ImageButton(actividad);

            a++;

            final String Url = url.get(i);
            final Bitmap imagen = elementos.get(i);


            img.setId(a);
            final int id_ = img.getId();
            img.setImageBitmap(redimensionarImagenMaximo(elementos.get(i), 300, 300));
            img.setBackgroundColor(Color.TRANSPARENT);
            img.setPadding(5, 20, 5, 20);
            fila.addView(img);
            img.setId(id_);

            img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Resumen.class);
                    intent.putExtra("url", Url);
                    intent.putExtra("imagen", imagen);
                    view.getContext().startActivity(intent);
                    actividad.overridePendingTransition(R.anim.derecha1, R.anim.derecha2);
                }
            });

            filas.add(fila);
            FILAS++;

        }

        tabla.addView(fila);


    }

    public void eliminarFila(int indicefilaeliminar) {
        if (indicefilaeliminar > 0 && indicefilaeliminar < FILAS) {
            tabla.removeViewAt(indicefilaeliminar);
            FILAS--;
        }
    }

    public int getFilas() {
        return FILAS;
    }

    public int getColumnas() {
        return COLUMNAS;
    }

    public int getCeldasTotales() {
        return FILAS * COLUMNAS;
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

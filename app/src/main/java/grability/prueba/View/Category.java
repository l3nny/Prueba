package grability.prueba.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import grability.prueba.DateBase.DB;
import grability.prueba.R;
import grability.prueba.Reader.Feader;
import grability.prueba.Reader.JSONParser;

public class Category extends Activity {

    private ImageButton update;
    AbsListView apps;
    ArrayAdapter<Feader> adaptador;

    DB base;
    JSONArray array;
    String Url = "https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        if (getResources().getBoolean(R.bool.landscape_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }


        base = new DB(this);
        base.open();


        if (isConnectingToInternet() == true) {
            base.deleteAll();
            new Attempt(apps).execute();
        } else if (base.emptyTable() == false) {
            new CargarListTask().execute();
        } else {
            showAlertDialog(Category.this, "Conexion a Internet",
                    "Tu Dispositivo no tiene Conexion a Internet.", false);
        }
        apps = (AbsListView) findViewById(R.id.list);

        apps.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(), List.class);
                Feader atributo = (Feader) apps.getItemAtPosition(position);
                intent.putExtra("Category", atributo.getCategory());
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_adelante1, R.anim.zoom_adelante2);
            }
        });
    }


    private class Attempt extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        AbsListView list;

        public Attempt(AbsListView apps) {
            this.list = apps;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Category.this);
            pDialog.setMessage("Actualizando");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            InputStream is = null;
            Bitmap bm = null;
            JSONParser sh = new JSONParser();
            String jsonStr = sh.makeServiceCall(Url, JSONParser.GET);

            if (jsonStr != null) {
                try {
                    base = new DB(context);
                    base.open();
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject c = jsonObj.getJSONObject("feed");
                    array = c.getJSONArray("entry");
                    final int numberOfItemsInResp = array.length();

                    for (int i = 0; i <= numberOfItemsInResp; i++) {
                        JSONObject z = array.getJSONObject(i);
                        JSONObject e = z.getJSONObject("im:name");
                        String Nombre = e.getString("label");
                        JSONArray w = z.getJSONArray("im:image");
                        JSONObject k = w.getJSONObject(2);
                        String UrlImage = k.getString("label");
                        JSONObject summary = z.getJSONObject("summary");
                        String Resumen = summary.getString("label");
                        JSONObject category = z.getJSONObject("category");
                        JSONObject attributes = category.getJSONObject("attributes");
                        String Categoria = attributes.getString("term");

                        is = (InputStream) new URL(UrlImage).getContent();
                        bm = BitmapFactory.decodeStream(is);
                        ByteArrayOutputStream blob = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, blob);
                        byte[] bitmapdata = blob.toByteArray();


                        base.insertApps(Nombre, Categoria, Resumen, UrlImage, bitmapdata);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (SQLiteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("", "Problemas para cargar el JSON");

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            new CargarListTask().execute();
        }
    }

    class CargarListTask extends AsyncTask<Void, String, ArrayAdapter> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected ArrayAdapter doInBackground(Void... arg0) {
            // TODO Auto-generated method stub

            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Feader[] listaCategorias = (Feader[]) base.listCategory();
            adaptador = new ArrayAdapter<Feader>(Category.this,
                    android.R.layout.simple_list_item_1, listaCategorias);

            return adaptador;
        }

        @Override
        protected void onPostExecute(ArrayAdapter result) {
            // TODO Auto-generated method stub
            apps.setAdapter(adaptador);

        }
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);

        alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        alertDialog.show();
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            @SuppressWarnings("deprecation")
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;

    }
}

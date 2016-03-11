package grability.prueba;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(getResources().getBoolean(R.bool.landscape_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                startActivity(new Intent(Splash.this, Categoria.class));
                overridePendingTransition(R.anim.desvanecer1, R.anim.desvanecer2);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

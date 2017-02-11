package com.universidadeafit.appeafit.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.universidadeafit.appeafit.R;

public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity.*/
                Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

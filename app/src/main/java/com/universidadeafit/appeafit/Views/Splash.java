package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Model.Intenciones;
import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends FragmentActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private UsuariosSQLiteHelper mydb;
    int id = 1;
    String nombres = "";

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mydb = new UsuariosSQLiteHelper(this);

          new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean aux;
                aux = mydb.HayUsuarios(id);
                if (aux) {
                   // Toast.makeText(getApplicationContext(), "TRUE hay base de datos", Toast.LENGTH_SHORT).show();
                    Cursor rs = mydb.ObtenerDatos(id);
                    nombres = rs.getString(2);

                    Toast.makeText(Splash.this, "¡"+" Hola " + nombres +" !", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Splash.this, NavigationDrawerActivity.class);

                    startActivity(i);
                    finish();

                } else {
                    //Toast.makeText(getApplicationContext(), "No hay base de datos SQLite", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}

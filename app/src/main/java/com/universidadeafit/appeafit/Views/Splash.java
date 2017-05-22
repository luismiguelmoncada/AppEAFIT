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
    ArrayList<Solicitud> resumen = new ArrayList<>();
    ArrayList<Solicitud> frecuentes = new ArrayList<>();
    ArrayList<Solicitud> sinresponder = new ArrayList<>();

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mydb = new UsuariosSQLiteHelper(this);

        GETIntenciones();
        GETCalificaciones();
        /*
        try {
            BMSClient.getInstance().initialize(this.getApplicationContext(), "", "", BMSClient.REGION_US_SOUTH);
        }
        catch (MalformedURLException e) {
            //The Bluemix region provided is invalid
        }

         Analytics.init(getApplication(), "AppEAFIT", "f364b59f-f7d2-4d30-ab6f-0053770c7720", Analytics.DeviceEvent.LIFECYCLE);
        */
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

    private void GETIntenciones(){
        Call<List<Intenciones>> call = ApiClient.get().getIntenciones("email");
        call.enqueue(new Callback<List<Intenciones>>() {
            @Override
            public void onResponse(Call<List<Intenciones>> call, Response<List<Intenciones>> response) {
                try {
                    List<Intenciones> preguntas = response.body();
                    if(response.body().isEmpty()){
                        //Toast.makeText(NavigationDrawerActivity.this,"No hay preguntas en la nube", Toast.LENGTH_LONG).show();
                    }else {
                        for (Intenciones user : preguntas) {
                            //Toast.makeText(NavigationDrawerActivity.this, user.getIntencion()+"fecha : "+user.getFecha(), Toast.LENGTH_SHORT).show();
                            resumen.add(new Solicitud(user.getIntencion(), user.getUsuario(), R.drawable.ic_ini, " ", " ",""));
                        }
                    }
                }
                catch (Exception e) {
                    // Toast.makeText(LoginActivity.this,"Error de Conexión al Servidor", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Intenciones>> call, Throwable t) {
                //Log.d("my_tag", "ERROR: " + t.getMessage());
                //Toast.makeText(LoginActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Splash.this," No tienes conexión a Internet ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void GETCalificaciones(){
        Call<List<Usuario>> call = ApiClient.get().getCalificaciones("email");
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                try {
                    List<Usuario> users = response.body();
                    if(response.body().isEmpty()){

                    }else {

                        for (Usuario user : users) {
                            frecuentes.add(new Solicitud(user.getName(), user.getUsername(), R.drawable.ic_start,user.getEmail() ,user.getPassword() ,""));

                        }
                    }
                }
                catch (Exception e) {
                    //Toast.makeText(NavigationDrawerActivity.this,"Error de Conexión al Servidor", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(Splash.this," No tienes conexión a Internet ", Toast.LENGTH_LONG).show();
            }
        });
    }
}

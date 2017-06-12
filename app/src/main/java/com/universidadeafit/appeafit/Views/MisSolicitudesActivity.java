package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterSolicitudes;
import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisSolicitudesActivity extends AppCompatActivity {

    private UsuariosSQLiteHelper mydb ;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    String  email;
    ArrayList<Solicitud> persons = new ArrayList<>();

    ArrayList<Solicitud> preguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missolicitudes);
        verToolbar("Mis Solicitudes",true);
        mydb = new UsuariosSQLiteHelper(this);

        boolean aux;// los carros se ingresan desde la posicion cero y esto sirve para saber si hay por lo menos 1
        aux = mydb.HayPreguntas();
        //aux es un vallor de tipo boolean y devuelve s
        if (aux) {
            //Toast.makeText(getApplicationContext(), "hay preguntas guardados en sqlite", Toast.LENGTH_SHORT).show();
            ConsultaVehiculosSQlite(); //si es usuario antiguo con base de datos ya creada
        } else {
            Toast.makeText(getApplicationContext(), " No Tienes Solicitudes", Toast.LENGTH_SHORT).show();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapterSolicitudes(persons);
        mRecyclerView.setAdapter(mAdapter);
    }

    public  void verToolbar(String titulo,Boolean UpButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(UpButton);
    }

    public void ConsultaVehiculosSQlite() {
        //int numero = mydb.CantidadPreguntas(); //se sabe cuantos carros hay almacenados en la tabla sqlite de vehiculos
        //String numerodecarros = String.valueOf(numero) ;
        //Toast.makeText(getApplicationContext(), numerodecarros, Toast.LENGTH_SHORT).show();
        preguntas = mydb.getCartList();
        for( int i = 0 ; i < preguntas.size() ; i++ ){
            //Toast.makeText(getApplicationContext(), preguntas.get( i ).getPregunta(), Toast.LENGTH_SHORT).show();
            persons.add(new Solicitud("Pregunta " + String.valueOf(i+1) +": "+preguntas.get( i ).getPregunta(),"Fecha: "+ preguntas.get( i ).getFecha(), R.drawable.solicitudes,"Motivo: "+preguntas.get( i ).getMotivo(),"Observacion: "+preguntas.get( i ).getObservacion(),""));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapterSolicitudes) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapterSolicitudes
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
    }

}

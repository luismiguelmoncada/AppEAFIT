package com.universidadeafit.appeafit.Views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.universidadeafit.appeafit.Model.DataObject;
import com.universidadeafit.appeafit.R;
import com.universidadeafit.appeafit.Views.Adapters.MyRecyclerViewAdapterVehiculo1;

import java.util.ArrayList;

public class SolicitudActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);
        verToolbar("Solicitudes",true);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapterVehiculo1(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        com.github.clans.fab.FloatingActionButton fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new AlertDialog.Builder(SolicitudActivity.this)
                            .setIcon(R.drawable.ic_face_asistent)
                            .setTitle("Tu Asistente Personal")
                            .setMessage("Hola, bienvenido. Soy Javier y te ayudaré a resolver todas tus inquietudes respecto a la U. ¡Fresco!")
                            .setPositiveButton("Continuar", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(SolicitudActivity.this, AsistenteActivity.class);
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("Volver", null)
                            .show();
                }
        });
    }

    public  void verToolbar(String titulo,Boolean UpButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(UpButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapterVehiculo1) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapterVehiculo1
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList<DataObject> persons = new ArrayList<>();
        persons.add(new DataObject("Solicitud 1", "Fecha Solicitud1", R.drawable.im_logo_eafit_55,"Descripcion1","Descripcion sobre solicitud 1"));
        persons.add(new DataObject("Solicitud 2", "Fecha Solicitud2", R.drawable.im_logo_eafit_55, "Descripcion1", "Descripcion sobre solicitud 1"));
        persons.add(new DataObject("Solicitud 3", "Fecha Solicitud3", R.drawable.im_logo_eafit_55, "Descripcion1", "Descripcion sobre solicitud 1"));
        return persons;
    }
}

package com.universidadeafit.appeafit.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterSolicitudes;
import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.R;

import java.util.ArrayList;

public class MisSolicitudesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missolicitudes);
        verToolbar("Mis Solicitudes",true);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapterSolicitudes(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
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
        ((MyRecyclerViewAdapterSolicitudes) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapterSolicitudes
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
    }

    private ArrayList<Solicitud> getDataSet() {
        ArrayList<Solicitud> persons = new ArrayList<>();
        persons.add(new Solicitud("Solicitud 1", "Fecha Solicitud1", R.drawable.im_logo_eafit_55,"Descripcion1","Descripcion sobre solicitud 1"));
        persons.add(new Solicitud("Solicitud 2", "Fecha Solicitud2", R.drawable.im_logo_eafit_55, "Descripcion1", "Descripcion sobre solicitud 1"));
        persons.add(new Solicitud("Solicitud 3", "Fecha Solicitud3", R.drawable.im_logo_eafit_55, "Descripcion1", "Descripcion sobre solicitud 1"));
        return persons;
    }
}

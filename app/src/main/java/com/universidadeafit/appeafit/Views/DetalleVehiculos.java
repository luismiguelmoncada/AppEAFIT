package com.universidadeafit.appeafit.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.R;
import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterVehiculo1;

import java.util.ArrayList;

public class DetalleVehiculos extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private static String LOG_TAG = "CardViewActivity";
    String placa = "";
    String usuario;
    String ip = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_vehiculos);
        verToolbar("Detalle",true);

        placa = getIntent().getExtras().getString("placa");

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapterVehiculo1(getDataSet());
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
        ((MyRecyclerViewAdapterVehiculo1) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapterVehiculo1
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
    }

    private ArrayList<Solicitud> getDataSet() {
        ArrayList<Solicitud> persons = new ArrayList<>();
        persons.add(new Solicitud(placa, "23 years old", R.drawable.im_logo_eafit_55,"Descripcion","Material is the metaphor.\n\n"+

                "A material metaphor is the unifying theory of a rationalized space and a system of motion."+
                "The material is grounded in tactile reality, inspired by the study of paper and ink, yet "+
                "technologically advanced and open to imagination and magic.\n"+
                "Surfaces and edges of the material provide visual cues that are grounded in reality. The "+
                "use of familiar tactile attributes helps users quickly understand affordances. Yet the "+
                "flexibility of the material creates new affordances that supercede those in the physical "+
                "world, without breaking the rules of physics.\n" +
                "The fundamentals of light, surface, and movement are key to conveying how objects move, "+
                "interact, and exist in space and in relation to each other. Realistic lighting shows "+
                "seams, divides space, and indicates moving parts.\n\n"));
        persons.add(new Solicitud("Lavery Maiss", "25 years old", R.drawable.im_logo_eafit_55, "Vendo", "Vendo paisaje"));
        persons.add(new Solicitud("Lillie Watts", "35 years old", R.drawable.im_logo_eafit_55, "Vendo", "Vendo paisaje"));
        return persons;
    }

}

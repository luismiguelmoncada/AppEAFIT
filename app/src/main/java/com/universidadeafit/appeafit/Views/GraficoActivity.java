package com.universidadeafit.appeafit.Views;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.R;

import java.util.ArrayList;
import java.util.List;

import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraficoActivity extends AppCompatActivity {

    private View view;
    int total = 0;
    int[] intArray = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        GraficoGET();
        verToolbar("Estadístico",true);
    }

    public  void verToolbar(String titulo,Boolean UpButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(UpButton);
    }
    private void GraficoGET(){
        Call<List<Solicitud>> call = ApiClient.get().getGrafico("email");
        call.enqueue(new Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                try {
                    List<Solicitud> preguntas = response.body();
                    if(response.body().isEmpty()){
                        //Toast.makeText(NavigationDrawerActivity.this,"No hay preguntas en la nube", Toast.LENGTH_LONG).show();
                    }else {

                        int aux = 0;
                        for (Solicitud user : preguntas) {
                            intArray[aux] = Integer.valueOf(user.getMotivo());
                            aux ++;
                            //Log.d("my_tag", "VALUES: " + user.getMotivo());
                            //Toast.makeText(NavigationDrawerActivity.this, user.getPregunta()+" "+user.getMotivo(), Toast.LENGTH_SHORT).show();
                        }

                        for (int i=0; i<intArray.length; i++)
                        {
                            total = total + intArray[i];
                        }
                        //Log.d("my_tag", "TOTAL: " + String.valueOf(total));

                        PieView pieView = (PieView) findViewById(R.id.pie_view);
                        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
                        pieHelperArrayList.add(new PieHelper((float)(intArray[2] * 100)/total, Color.rgb(0,57,88)));
                        pieHelperArrayList.add(new PieHelper((float)(intArray[0] * 100)/total,Color.GRAY));
                        pieHelperArrayList.add(new PieHelper((float)(intArray[1] * 100)/total));
                        pieHelperArrayList.add(new PieHelper((float)((intArray[3] * 100)/total),Color.rgb(7,221,255)));

                        pieView.setDate(pieHelperArrayList);
                        pieView.selectedPie(1); //optional
                    }
                }
                catch (Exception e) {
                    // Toast.makeText(LoginActivity.this,"Error de Conexión al Servidor", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                //Log.d("my_tag", "ERROR: " + t.getMessage());

            }
        });
    }
}

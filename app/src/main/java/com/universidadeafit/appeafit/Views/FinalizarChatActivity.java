package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Adapters.ApiRest.ServerResponse;
import com.universidadeafit.appeafit.Model.Constants;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalizarChatActivity extends AppCompatActivity {

    private Spinner spinnerSolucion,spinnerSugerencia;
    String message,result,email;
    private UsuariosSQLiteHelper mydb ;

    @BindView(R.id.observacion_pregunta)
    EditText Observacion;

    @BindView(R.id.volver_chat)
    Button button_volver;

    @BindView(R.id.enviar)
    Button button_guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_chat);
        ButterKnife.bind(this);
        mydb = new UsuariosSQLiteHelper(this);
        boolean aux;
        aux = mydb.HayUsuarios(1);

        if (aux) {
            Cursor rs = mydb.ObtenerDatos(1);
            email = rs.getString(5);
        }
    }

    @OnClick(R.id.volver_chat)
    public void Volver_Chat(){

        spinnerSolucion = (Spinner) findViewById(R.id.spinner_solucion);
        spinnerSugerencia = (Spinner) findViewById(R.id.spinner_sugerencia);
        String observacion = Observacion.getText().toString();
       //Toast.makeText(FinalizarChatActivity.this,String.valueOf(spinnerSolucion.getSelectedItem())+String.valueOf(spinnerSugerencia.getSelectedItem())+observacion , Toast.LENGTH_LONG).show();
        RegistrarCalificacion(email,String.valueOf(spinnerSolucion.getSelectedItem()),String.valueOf(spinnerSugerencia.getSelectedItem()),observacion,getDateTime());

        FinalizarChatActivity.this.finish();
        WatsonActivity.fa.finish();
    }

    @OnClick(R.id.enviar)
    public void GuardarPregunta(){
        FinalizarChatActivity.this.finish();
    }


    private void RegistrarCalificacion(String email,String solucion,String sugerencia,String observacion,String fecha){

        //Toast.makeText(FinalizarChatActivity.this,email+solucion+sugerencia+observacion+fecha , Toast.LENGTH_LONG).show();

        Usuario usuario = new Usuario(email,solucion,sugerencia,observacion,fecha,"","");
        Call<ServerResponse> call = ApiClient.get().insertarCalificacion(usuario);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                message = response.body().getMessage();
                result = response.body().getResult();
                Toast.makeText(FinalizarChatActivity.this, message, Toast.LENGTH_LONG).show();

                if (result.equals(Constants.SUCCESS)){

                }else{
                    //prueba de captura de error con firebase, cuando el usuario ya existe

                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(FinalizarChatActivity.this,"Server Error : "+ t.getMessage(), Toast.LENGTH_LONG).show();
                //Toast.makeText(FinalizarChatActivity.this," No tienes conexión a Internet ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}

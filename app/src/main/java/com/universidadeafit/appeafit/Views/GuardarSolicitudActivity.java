package com.universidadeafit.appeafit.Views;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Adapters.ApiRest.ServerResponse;
import com.universidadeafit.appeafit.Model.Constants;
import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardarSolicitudActivity extends AppCompatActivity {

    private UsuariosSQLiteHelper mydb ;
    String Pregunta_Usuario,RespuestaWatson,email;
    String message,result;
    private Spinner spinnerMotivo;

    int id = 1;

    @BindView(R.id.pregunta)
    TextView Pregunta;

    @BindView(R.id.observacion_pregunta)
    EditText Observacion;

    @BindView(R.id.volver_chat)
    Button button_volver;

    @BindView(R.id.enviar)
    Button button_guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_solicitud);
        ButterKnife.bind(this);

        Bundle bundle =  this.getIntent().getExtras();
        Pregunta_Usuario = bundle.getString("pregunta");
        RespuestaWatson = bundle.getString("respuestaWatson");
        Pregunta.setText(Pregunta_Usuario);

        mydb = new UsuariosSQLiteHelper(this);
        boolean aux;
        aux = mydb.HayUsuarios(id);
        if (aux) {
            Cursor rs = mydb.ObtenerDatos(id);
            email = rs.getString(5);
        }
       // Toast.makeText(GuardarSolicitudActivity.this,Pregunta_Usuario+cleanString(RespuestaWatson) , Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.volver_chat)
    public void Volver_Chat(){
        GuardarSolicitudActivity.this.finish();
    }

    @OnClick(R.id.enviar)
    public void GuardarPregunta(){

        spinnerMotivo = (Spinner) findViewById(R.id.spinner_motivo_pregunta);
        String observacion = Observacion.getText().toString();
        //Toast.makeText(GuardarSolicitudActivity.this,Pregunta_Usuario+email+String.valueOf(spinnerMotivo.getSelectedItem())+observacion+getDateTime() , Toast.LENGTH_LONG).show();

        boolean aux = mydb.PreguntaExiste(Pregunta_Usuario);

        if (aux) {
            //Toast.makeText(getApplicationContext(), "ya existe", Toast.LENGTH_SHORT).show();

        } else {
            //Toast.makeText(getApplicationContext(), " no existe", Toast.LENGTH_SHORT).show();
            InsertarSQlite(Pregunta_Usuario,String.valueOf(spinnerMotivo.getSelectedItem()),observacion,getDateTime());
            //ActualizarDatos();// si es un usuario nuevo
        }

        InsertarPreguntaUsuario(Pregunta_Usuario,String.valueOf(spinnerMotivo.getSelectedItem()),observacion,email,cleanString(RespuestaWatson));
        GuardarSolicitudActivity.this.finish();
    }

    public void InsertarSQlite(String pregunta, String  motivo, String observacion, String fecha) {
        mydb.InsertarPregunta(pregunta.toString(), motivo.toString(), observacion.toString(), fecha.toString());
    }

    private void InsertarPreguntaUsuario(String pregunta, String  motivo, String observacion, String email,String respuesta){

        Solicitud solicitud = new Solicitud(pregunta,motivo,0,observacion,email,respuesta);
        Call<ServerResponse> call = ApiClient.get().insertarPregunta(solicitud);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                message = response.body().getMessage();
                result = response.body().getResult();
                Toast.makeText(GuardarSolicitudActivity.this, message, Toast.LENGTH_LONG).show();

                if (result.equals(Constants.SUCCESS)){
                    //Toast.makeText(GuardarSolicitudActivity.this,"Datos Almacenados Correctamente en mysql", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                //Toast.makeText(GuardarSolicitudActivity.this,"Server Error : "+ t.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(GuardarSolicitudActivity.this," Error de Servidor... ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
//para quitarle las tildes a la respuesta watson y guarden bn en mysql
    public static String cleanString(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }
}

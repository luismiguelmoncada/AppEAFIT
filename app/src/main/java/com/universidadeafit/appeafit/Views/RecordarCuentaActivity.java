package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Model.SendMail;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordarCuentaActivity extends AppCompatActivity {

    @BindView(R.id.recordar_cuenta_email)
    EditText Email;

    @BindView(R.id.volver)
    Button button_volver;

    @BindView(R.id.enviar)
    Button button_recordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordar_cuenta);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.volver)
    public void Volver_login(){
        RecordarCuentaActivity.this.finish();
    }

    @OnClick(R.id.enviar)
    public void Enviar_login(){
        Validaar();
    }

    public void Validaar(){

        Email.setError(null);
        boolean cancel = false;
        View focusView = null;
        String email = Email.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Email.setError(getString(R.string.error_field_required));
            focusView = Email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            Email.setError(getString(R.string.error_invalid_email));
            focusView = Email;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            //Toast.makeText(RecordarCuentaActivity.this,email, Toast.LENGTH_LONG).show();
            EnviarEmail(email);
        }
    }



    public void EnviarEmail(String email){

        Call<List<Usuario>> callrecord = ApiClient.get().getRecordarUsers(email);
        callrecord.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> users = response.body();

                if(response.body().isEmpty()){
                    Toast.makeText(RecordarCuentaActivity.this,"Email no registrado", Toast.LENGTH_LONG).show();
                }else {
                    for (Usuario user : users) {
                        sendEmail(user.getName(),user.getUsername(),user.getEmail(),user.getPassword());
                        //Toast.makeText(RecordarCuentaActivity.this, user.getName()+" , "+user.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                //Log.d("my_tag", "ERROR: " + t.getMessage());
                //Toast.makeText(LoginActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(RecordarCuentaActivity.this," No tienes conexión a Internet ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendEmail(String nombre, String apellidos,String email,String contraseña) {
        //Getting content for email
        String subject = "[AppEAFIT] - Confirmación de Registro";
        String message = "Hola "+nombre+". AppEAFIT te da la bienvenida."+"\n"+"\n"+"Recuerda que con nuestra aplicación puedes resolver todas tus inquietudes respecto " +
                "al funcionamiento de EAFIT Interactiva. Conversa con nuestro Bot en su primera versión y ayúdanos a mejorarlo."+ "\n"+ "\n" +
                "Tus credenciales de ingreso son:"+ "\n"+ "\n" +"Usuario: "+email+"\n"+"Contraseña: "+contraseña+"\n"+"\n"+"Este es un proyecto realizado " +
                "por estudiantes del programa de Ingeniería de Sistemas de la universidad EAFIT en la materia Proyecto Integrador 2 con el apoyo de sus profesores a cargo."+ "\n"+ "\n"+"\n"+ "\n"+ "\n"+"\n";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);
        //Executing sendmail to send email
        sm.execute();
    }

    private boolean isEmailValid(String email) {
        String emailbuscar = "@";
        int contador = 0;
        String dominio = "@eafit.edu.co";
        String buscardominio = "";
        String emailaux = email;
        //Controla que no hallan varios @ en el campo email
        if(email.indexOf(emailbuscar) > -1) {
            while (email.indexOf(emailbuscar) > -1) {
                email = email.substring(email.indexOf(
                        emailbuscar) + emailbuscar.length(), email.length());
                contador++;
            }
            //Extrae toda la cedena despues de encontrar un @ y solo acepta si esa cedena es igual al dominio
            buscardominio = emailaux.substring(emailaux.indexOf(emailbuscar),emailaux.length());
            if(buscardominio.equals(dominio)){
                return  true;
            }
            //Toast.makeText(LoginActivity.this,String.valueOf(buscardominio), Toast.LENGTH_LONG).show();
        }
        if(contador > 1){
            return false;
        }
        return false;
    }
}

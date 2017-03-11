package com.universidadeafit.appeafit.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Adapters.ApiRest.ServerResponse;
import com.universidadeafit.appeafit.Model.Constants;
import com.universidadeafit.appeafit.Model.SendMail;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    String nombre, apellidos,email,contraseña,message,result;

    //Se usa la libreria jakewharton:butterknife para injeccion de vistas
    @BindView(R.id.editTextName)
    EditText Nombre;

    @BindView(R.id.editTextUsername)
    EditText Apellidos;

    @BindView(R.id.editTextEmail)
    EditText Email;

    @BindView(R.id.buttonRegister)
    Button BotonRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        verToolbar(getResources().getString(R.string.toolbar_titulo),true);
        ButterKnife.bind(this);


        contraseña="12345";

    }

    public  void verToolbar(String titulo,Boolean UpButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(UpButton);
    }

    @OnClick(R.id.buttonRegister)
    public void Registro(){
        IngresarUsuarioNuevo();
    }

    private void IngresarUsuarioNuevo(){

        Nombre.setError(null);
        Apellidos.setError(null);
        Email.setError(null);


        boolean cancel = false;
        View focusView = null;

         nombre = Nombre.getText().toString();
         apellidos = Apellidos.getText().toString();
         email = Email.getText().toString();


        // Check for a valid input parametres.
        if (TextUtils.isEmpty(nombre)) {
            Nombre.setError(getString(R.string.error_field_required));
            focusView = Nombre;
            cancel = true;
        }else if (TextUtils.isEmpty(apellidos)) {
            Apellidos.setError(getString(R.string.error_field_required));
            focusView = Apellidos;
            cancel = true;
        }else if (TextUtils.isEmpty(email)) {
            Email.setError(getString(R.string.error_field_required));
            focusView = Email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            Email.setError(getString(R.string.error_invalid_email));
            focusView = Email;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

           //sendEmail(nombre,apellidos,email,contraseña);
           RegistrarUsuario(nombre, apellidos,contraseña,email);
        }
    }

    private void sendEmail(String nombre, String apellidos,String email,String contraseña) {
        //Getting content for email
        String subject = "[AppEAFIT] - Confirmación de Registro";
        String message = "Hola "+nombre+". AppEAFIT te da la bienvenida."+"\n"+"\n"+"Recuerda que con nuestra aplicación puedes resolver todas tus inquietudes respecto " +
                "al funcionamiento de EAFIT Interactiva. Conversa con nuestro Bot en su primera versión y ayúdanos a mejorarlo."+ "\n"+ "\n" +
                "Tus credenciales de ingreso son:"+ "\n"+ "\n" +"Usuario: "+email+"\n"+"Contraseña: "+contraseña+"\n"+"\n"+"Este es un proyecto realizado " +
                "por estudiantes del programa de Ingeniería de Sistemas de la universidad EAFIT en la materia Proyecto Integrador 2."+ "\n"+ "\n"+"\n"+ "\n"+ "\n"+"\n";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);
        //Executing sendmail to send email
        sm.execute();
    }
    private void RegistrarUsuario(String nombre,String apellidos,String contraseña,String email){

        Usuario usuario = new Usuario(nombre,apellidos,contraseña,email);
        final String nombreLog =nombre;
        final String apellidosLog =apellidos;
        final String contraseñaLog =contraseña;
        final String emailLog =email;
        Call<ServerResponse> call = ApiClient.get().createUser(usuario);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                message = response.body().getMessage();
                result = response.body().getResult();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();

                if (result.equals(Constants.SUCCESS)){
                    sendEmail(nombreLog,apellidosLog,emailLog,contraseñaLog);
                    LoginActivity login = new LoginActivity();
                    EditText emailLogin = login.retornarEmail();
                    emailLogin.setText(emailLog);
                    //RegisterActivity.this.finish();
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                //Toast.makeText(RegisterActivity.this,"Server Error : "+ t.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(RegisterActivity.this," No tienes conexión a Internet ", Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

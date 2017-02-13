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
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    String nombre,nombreusuario,email,contraseña,message,result;

    //Se usa la libreria jakewharton:butterknife para injeccion de vistas
    @BindView(R.id.editTextName)
    EditText Nombre;

    @BindView(R.id.editTextUsername)
    EditText NombreUsuario;

    @BindView(R.id.editTextPassword)
    EditText Contraseña;

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
        NombreUsuario.setError(null);
        Email.setError(null);
        Contraseña.setError(null);

        boolean cancel = false;
        View focusView = null;

         nombre = Nombre.getText().toString();
         nombreusuario = NombreUsuario.getText().toString();
         email = Email.getText().toString();
         contraseña = Contraseña.getText().toString();

        // Check for a valid input parametres.
        if (TextUtils.isEmpty(nombre)) {
            Nombre.setError(getString(R.string.error_field_required));
            focusView = Nombre;
            cancel = true;
        }else if (TextUtils.isEmpty(nombreusuario)) {
            NombreUsuario.setError(getString(R.string.error_field_required));
            focusView = NombreUsuario;
            cancel = true;
        }else if (TextUtils.isEmpty(email)) {
            Email.setError(getString(R.string.error_field_required));
            focusView = Email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            Email.setError(getString(R.string.error_invalid_email));
            focusView = Email;
            cancel = true;
        } else if (TextUtils.isEmpty(contraseña)) {
            Contraseña.setError(getString(R.string.error_field_required));
            focusView = Contraseña;
            cancel = true;
        }else if (!isPasswordValid(contraseña)){
            Contraseña.setError(getString(R.string.error_invalid_password));
            focusView = Contraseña;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            RegistrarUsuario(nombre,nombreusuario,contraseña,email);
        }
    }

    private void RegistrarUsuario(String nombre,String nombreusuario,String contraseña,String email){

        Usuario usuario = new Usuario(nombre,nombreusuario,contraseña,email);
        final String emailLog =email;
        Call<ServerResponse> call = ApiClient.get().createUser(usuario);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                message = response.body().getMessage();
                result = response.body().getResult();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();

                if (result.equals(Constants.SUCCESS)){
                    LoginActivity login = new LoginActivity();
                    EditText emailLogin = login.retornarEmail();
                    emailLogin.setText(emailLog);
                    RegisterActivity.this.finish();
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,"Server Error : "+ t.getMessage(), Toast.LENGTH_LONG).show();
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

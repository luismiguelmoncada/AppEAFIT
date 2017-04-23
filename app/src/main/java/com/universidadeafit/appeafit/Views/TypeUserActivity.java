package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Adapters.ApiRest.ServerResponse;
import com.universidadeafit.appeafit.Model.Constants;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeUserActivity extends AppCompatActivity {

    private UsuariosSQLiteHelper mydb ;
    private Spinner spinner1;
    String message,result,email;
    @BindView(R.id.codigo)
    EditText Codigo;

    @BindView(R.id.identificacion)
    EditText Identificacion;

    @BindView(R.id.buttonini)
    Button button_ini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_user);
        ButterKnife.bind(this);
        mydb = new UsuariosSQLiteHelper(this);
        email = getIntent().getExtras().getString("email");
    }

    @OnClick(R.id.buttonini)
    public void Ingresar(){

        //Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();

        Codigo.setError(null);
        Identificacion.setError(null);
        boolean cancel = false;
        View focusView = null;

        String codigo = Codigo.getText().toString();
        String identificacion = Identificacion.getText().toString();
        spinner1 = (Spinner) findViewById(R.id.spinnerRol);

        if (TextUtils.isEmpty(codigo)) {
            Codigo.setError(getString(R.string.error_field_required));
            focusView = Codigo;
            cancel = true;
        } else if (TextUtils.isEmpty(identificacion)) {
            Identificacion.setError(getString(R.string.error_field_required));
            focusView = Identificacion;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();

        } else {
            InsertarSQlite(1, String.valueOf(spinner1.getSelectedItem()), codigo, identificacion);
            RegistrarTipoUsuario(String.valueOf(spinner1.getSelectedItem()), codigo,identificacion,email);
            //Toast.makeText(getApplicationContext(), String.valueOf(spinner1.getSelectedItem()) + codigo + identificacion, Toast.LENGTH_SHORT).show();
            TypeUserActivity.this.finish();
        }
    }

    public void InsertarSQlite(Integer id, String rol, String codigo,String identificacion) {
        mydb.InsertarTipoUsuario(id, rol.toString(), codigo.toString(), identificacion.toString());
    }

    private void RegistrarTipoUsuario(String rol,String codigo,String identificacion,String email){

        Usuario usuario = new Usuario(rol,codigo,identificacion,email);
        Call<ServerResponse> call = ApiClient.get().insertarTipoUser(usuario);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                message = response.body().getMessage();
                result = response.body().getResult();
                Toast.makeText(TypeUserActivity.this, message, Toast.LENGTH_LONG).show();

                if (result.equals(Constants.SUCCESS)){
                    //Toast.makeText(TypeUserActivity.this,"Datos Almacenados Correctamente en mysql", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                //Toast.makeText(RegisterActivity.this,"Server Error : "+ t.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(TypeUserActivity.this," No tienes conexión a Internet ", Toast.LENGTH_LONG).show();
            }
        });
    }
}

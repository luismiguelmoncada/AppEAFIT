package com.universidadeafit.appeafit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.Views.LoginActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.get)
    Button button_get;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.get)
    public void get(){
        LoginServerGET();
    }

    private void LoginServerGET(){
        // Ejemplo para uso de get


        String email="lmoncad@eafit.edu.co";
        String password="lmoncadG4ZS";

        //Toast.makeText(MainActivity.this, "GET", Toast.LENGTH_SHORT).show();
        Call<List<Usuario>> call = ApiClient.get().getUsers(email,password);

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> users = response.body();
                TextView name = (TextView)findViewById(R.id.view1);
                TextView apellidos = (TextView)findViewById(R.id.view2);
                TextView contraseña = (TextView)findViewById(R.id.view3);
                TextView email = (TextView)findViewById(R.id.view4);
                for (Usuario user : users) {
                    //Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    name.setText(user.getName());
                    apellidos.setText(user.getUsername());
                    contraseña.setText(user.getPassword());
                    email.setText(user.getEmail());

                    Log.d("my_tag", "Response: " + users.toString());
                   // Toast.makeText(MainActivity.this, user.getName()+" , "+user.getEmail(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.d("my_tag", "ERROR: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

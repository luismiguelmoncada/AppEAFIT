package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.universidadeafit.appeafit.MainActivity;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;
import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Model.Usuario;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    static EditText Email; //Para acceder a el desde RegisterActivity @BindView(R.id.email) EditText email;
    private UsuariosSQLiteHelper mydb ;
    //libreria para evitar usar tanto codigo especialmente con los onclic, puedo hacer injeccion de codigo
    @BindView(R.id.password) EditText Password;

    @BindView(R.id.CreateAccount)
    TextView create_account;

    @BindView(R.id.RememberAccount)
    TextView remember_account;

    @BindView(R.id.login)
    Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Email = (EditText) findViewById(R.id.email);
        mydb = new UsuariosSQLiteHelper(this);
    }

    @OnClick(R.id.RememberAccount)
    public void RecordarCuenta(){
        Intent i = new Intent(LoginActivity.this, RecordarCuentaActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.CreateAccount)
    public void NuevaCuenta(){
        //Toast.makeText(LoginActivity.this, "Prueba libreria JakeWharton", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.login)
    public void Ingresar(){
        Login();
    }

    public EditText retornarEmail()    {
        return this.Email;
    }

    private void Login() {

        Email.setError(null);
        Password.setError(null);
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        //String email = "lmoncad1@eafit.edu.co";
        //String password = "1234567890";

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            Password.setError(getString(R.string.error_invalid_password));
            focusView = Password;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            Password.setError(getString(R.string.error_field_required));
            focusView = Password;
            cancel = true;
        }
        // Check for a valid email address.
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
            ValidarUsuarioGET(email,password);
           /* InsertarSQlite(1, "Usuario", "Luis Miguel", "Moncada ocampo", "1234567890", "lmoncad1@eafit.edu.co");
            Toast.makeText(LoginActivity.this, "¡" + " Hola " + "Luis Miguel" + " !", Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
            LoginActivity.this.startActivity(mainIntent);
            LoginActivity.this.finish();
            */
        }
    }

    private void ValidarUsuarioGET(String email, String contraseña){
        Call<List<Usuario>> call = ApiClient.get().getUsers(email,contraseña);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> users = response.body();

                if(response.body().isEmpty()){
                    Toast.makeText(LoginActivity.this,"Email o contraseña no validos", Toast.LENGTH_LONG).show();
                }else {
                    for (Usuario user : users) {
                        //Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        InsertarSQlite(1, "Usuario", user.getName(), user.getUsername(), user.getPassword(), user.getEmail());
                        Toast.makeText(LoginActivity.this, "¡" + " Hola " + user.getName() + " !", Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(LoginActivity.this, TypeUserActivity.class);
                        LoginActivity.this.startActivity(mainIntent);
                        LoginActivity.this.finish();
                        //Toast.makeText(MainActivity.this, user.getName()+" , "+user.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                //Log.d("my_tag", "ERROR: " + t.getMessage());
                //Toast.makeText(LoginActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this," No tienes conexión a Internet ", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void InsertarSQlite(Integer id, String nombre, String  NombreCompleto, String apellidos, String pass, String  email) {
        mydb.InsertarUsuario(id, nombre.toString(), NombreCompleto.toString(), apellidos.toString(), pass.toString(), email.toString());
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

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    /*
    private void ValidarUsuario(String email, String contraseña){

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(contraseña);
        final String contraseñaLog = contraseña;
        final String emailLog = email;

        Call<ServerResponse> call = ApiClient.get().obtenerUsuario(usuario);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                String message = response.body().getMessage();
                String result = response.body().getResult();
                List<Usuario> users =  response.body().getUser();
                ArrayList<String> nombres = new ArrayList<>();
                ArrayList<String> apellidos = new ArrayList<>();

                if (result.equals(Constants.SUCCESS)){
                    try {
                        JSONArray jArray = new JSONArray(users);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            nombres.add(json.getString("name"));
                            apellidos.add(json.getString("username"));
                        }
                    }catch (JSONException e ){
                        e.printStackTrace();
                    }
                    Log.d("my_tag", users.toString() );
                    //InsertarSQlite(1, "Usuario", nombres.get(0).trim(), apellidos.get(0).trim(), contraseñaLog, emailLog);
                    InsertarSQlite(1, "Usuario", "Nombre Usuario", "Apellidos", contraseñaLog, emailLog);
                    Intent mainIntent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();
                }
                if(message.equals("Bienvenido")){
                //    Toast.makeText(LoginActivity.this, "¡"+" Hola " + nombres.get(0).trim() +" !", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Server Error : "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void LoginServerGET(){
        // Ejemplo para uso de get
        Call<List<Usuario>> call = ApiClient.get().getUsers();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> users = response.body();
                // TextView name = (TextView)findViewById(R.id.textView4);
                //TextView email = (TextView)findViewById(R.id.textView5);
                for (Usuario user : users) {
                    Toast.makeText(LoginActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    //name.setText(user.getName());
                    //email.setText(user.getEmail());
                    Toast.makeText(LoginActivity.this, user.getName()+" , "+user.getEmail(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.d("my_tag", "ERROR: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}

package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterPerfil;
import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterSolicitudes;
import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PerfilActivity extends AppCompatActivity {

    private UsuariosSQLiteHelper mydb ;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    ArrayList<Solicitud> datosusuario = new ArrayList<>();
    String nombres,apellidos,email,rol,codigo,identificacion;

    @BindView(R.id.text_nombrecompleto)
    TextView nombrecompleto;

    @BindView(R.id.text_toolbar_nombre)
    TextView nombretoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ButterKnife.bind(this);
        verToolbar("",true);

        mydb = new UsuariosSQLiteHelper(this);
        boolean aux;
        boolean auxtipousu;
        aux = mydb.HayUsuarios(1);
        auxtipousu = mydb.HayTipoUsuarios(1);

        if (aux) {
            Cursor rs = mydb.ObtenerDatos(1);
            nombres = rs.getString(2);
            apellidos = rs.getString(3);
            email = rs.getString(5);
        }

        if (auxtipousu) {
            Cursor rs = mydb.ObtenerDatosTipoUsuario(1);
            rol = rs.getString(1);
            codigo = rs.getString(2);
            identificacion = rs.getString(3);
        }else{
            rol = "";
            codigo = "";
            identificacion = "";
        }

        nombrecompleto.setText(nombres + " " + apellidos);
        nombretoolbar.setText(nombres);

        datosusuario.add(new Solicitud("Email", email, R.drawable.ic_email_perfil, "", "",""));
        datosusuario.add(new Solicitud("Rol", rol, R.drawable.ic_perfil_perfil, "", "",""));
        datosusuario.add(new Solicitud("Identificación", identificacion, R.drawable.ic_identificacion, "", "",""));
        datosusuario.add(new Solicitud("Codigo", codigo, R.drawable.ic_codigo, "", "",""));
        datosusuario.add(new Solicitud("Solicitudes", "12", R.drawable.ic_pregunta_perfil, "", "",""));

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_perfil);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapterPerfil(datosusuario);
        mRecyclerView.setAdapter(mAdapter);

    }

    public  void verToolbar(String titulo,Boolean UpButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(UpButton);
    }


}

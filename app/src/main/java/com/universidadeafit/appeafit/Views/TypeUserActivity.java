package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TypeUserActivity extends AppCompatActivity {

    private UsuariosSQLiteHelper mydb ;

    @BindView(R.id.buttonini)
    Button button_ini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_user);
        ButterKnife.bind(this);
        mydb = new UsuariosSQLiteHelper(this);
    }

    @OnClick(R.id.buttonini)
    public void Ingresar(){
        Intent mainIntent = new Intent(TypeUserActivity.this, NavigationDrawerActivity.class);
        TypeUserActivity.this.startActivity(mainIntent);
        TypeUserActivity.this.finish();
    }
}

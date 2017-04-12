package com.universidadeafit.appeafit.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.universidadeafit.appeafit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuardarSolicitudActivity extends AppCompatActivity {


    String Pregunta_Usuario;

    @BindView(R.id.pregunta)
    TextView Pregunta;

    @BindView(R.id.recordar_cuenta_email)
    EditText Email;

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
        Pregunta.setText(Pregunta_Usuario);

        //Toast.makeText(GuardarSolicitudActivity.this,Pregunta_Usuario , Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.volver_chat)
    public void Volver_Chat(){
        GuardarSolicitudActivity.this.finish();
    }
}

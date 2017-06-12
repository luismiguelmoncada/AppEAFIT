package com.universidadeafit.appeafit.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterPerfil;
import com.universidadeafit.appeafit.Adapters.MyRecyclerViewAdapterSolicitudes;
import com.universidadeafit.appeafit.Model.Solicitud;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity {

    private UsuariosSQLiteHelper mydb ;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private static int SELECT_PICTURE = 2;

    ArrayList<Solicitud> datosusuario = new ArrayList<>();
    String nombres,apellidos,email,rol,codigo,identificacion;

    @BindView(R.id.text_nombrecompleto)
    TextView nombrecompleto;

    @BindView(R.id.text_toolbar_nombre)
    TextView nombretoolbar;

    @BindView(R.id.editar)
    TextView update_account;

    @BindView(R.id.image_perfil)
    CircleImageView profile;

    static PerfilActivity activityA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ButterKnife.bind(this);
        verToolbar("",true);

        activityA = this;

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

        datosusuario.add(new Solicitud("Email", email, R.drawable.correo, "", "",""));
        datosusuario.add(new Solicitud("Rol", rol, R.drawable.administrativo, "", "",""));
        datosusuario.add(new Solicitud("Identificación", identificacion, R.drawable.identificacion, "", "",""));
        datosusuario.add(new Solicitud("Codigo", codigo, R.drawable.codigo, "", "",""));
        datosusuario.add(new Solicitud("Solicitudes", "12", R.drawable.solicitudes, "", "",""));

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_perfil);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapterPerfil(datosusuario);
        mRecyclerView.setAdapter(mAdapter);

        SharedPreferences myPrefrence = getPreferences(MODE_PRIVATE);
        String imageS = myPrefrence.getString("imagePreferance", "");
        Bitmap imageB = null;

        if(!imageS.equals("")) {
            imageB = decodeBase64(imageS);
            CircleImageView im = (CircleImageView)findViewById(R.id.image_perfil);
            im.setImageBitmap(imageB);
        }

    }

    public static PerfilActivity getInstance(){
        return   activityA;
    }

    @OnClick(R.id.editar)
    public void editarperfil(){
        Intent mainIntent = new Intent(PerfilActivity.this, TypeUserActivity.class);
        mainIntent.putExtra("email",email);
        PerfilActivity.this.startActivity(mainIntent);
    }

    @OnClick(R.id.image_perfil)
    public void editarimagenperfil(){

        Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int code = SELECT_PICTURE;
        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(intent, code);

        //Intent mainIntent = new Intent(PerfilActivity.this, GaleryActivity.class);
        //PerfilActivity.this.startActivity(mainIntent);
    }

    public  void verToolbar(String titulo,Boolean UpButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(UpButton);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * Se revisa si la imagen viene de la c‡mara (TAKE_PICTURE) o de la galer’a (SELECT_PICTURE)
         */
       if (requestCode == SELECT_PICTURE){

            try {
                Uri selectedImage = data.getData();
                InputStream is;

                is = getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bis = new BufferedInputStream(is);
                Bitmap bitmap = BitmapFactory.decodeStream(bis);


                SharedPreferences myPrefrence = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefrence.edit();
                editor.putString("namePreferance", "perfil");
                editor.putString("imagePreferance", encodeTobase64(bitmap));
                editor.commit();

                CircleImageView im = (CircleImageView)findViewById(R.id.image_perfil);
                im.setImageBitmap(bitmap);

            } catch (Exception e) {

            }
        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}

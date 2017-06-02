package com.universidadeafit.appeafit.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

import com.universidadeafit.appeafit.Adapters.ApiRest.ApiClient;
import com.universidadeafit.appeafit.Adapters.ApiRest.ServerResponse;
import com.universidadeafit.appeafit.Adapters.MyRecyclerViewChatAdapter;
import com.universidadeafit.appeafit.Model.Constants;
import com.universidadeafit.appeafit.Model.Intenciones;
import com.universidadeafit.appeafit.Model.Message;
import com.universidadeafit.appeafit.Model.Usuario;
import com.universidadeafit.appeafit.Model.UsuariosSQLiteHelper;
import com.universidadeafit.appeafit.R;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.*;
import com.ibm.mobilefirstplatform.clientsdk.android.analytics.api.*;
import com.ibm.mobilefirstplatform.clientsdk.android.logger.api.*;

import org.json.JSONObject;

import retrofit2.*;

import static com.universidadeafit.appeafit.Views.PerfilActivity.decodeBase64;


public class WatsonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    String preguntanot = "";
    String respuestaWatson = "";
    String message,result,email;
    private MyRecyclerViewChatAdapter mAdapter;
    private ArrayList messageArrayList;
    private EditText inputMessage;
    private ImageButton btnSend;
    private Map<String,Object> context = new HashMap<>();
    private UsuariosSQLiteHelper mydb ;

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watson);
        verToolbar("usuario",true);
        mydb = new UsuariosSQLiteHelper(this);
        boolean aux;
        aux = mydb.HayUsuarios(1);
        fa = this;

        if (aux) {
            Cursor rs = mydb.ObtenerDatos(1);
            email = rs.getString(5);
        }
/*
        BMSClient.getInstance().initialize(getApplicationContext(), BMSClient.REGION_US_SOUTH); // Make sure that you point to your region
        // In this code example, Analytics is configured to record lifecycle events.
        Analytics.init(getApplication(), "AppEAFIT", "f364b59f-f7d2-4d30-ab6f-0053770c7720", false, Analytics.DeviceEvent.ALL);

        Analytics.enable();
        enviarAlalytics();//Envia el registro de conexion de usuarios al bot
*/
        inputMessage = (EditText) findViewById(R.id.message);
        btnSend = (ImageButton) findViewById(R.id.btn_send);

        messageArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewChatAdapter(messageArrayList);
        recyclerView.setAdapter(mAdapter);

        //sendMessage(); //Para q watson inicie la conversacion

        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkInternetConnection()) {
                    sendMessage();
                }
            }
        });

    };

    public  void verToolbar(String titulo,Boolean UpButton){


        SharedPreferences myPrefrence = getPreferences(MODE_PRIVATE);
        String imageS = myPrefrence.getString("imagePreferance", "");
        Bitmap imageB = null;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);

        if(!imageS.equals("")) {
            imageB = decodeBase64(imageS);
            Drawable drawable = new BitmapDrawable(getResources(), imageB);
            getSupportActionBar().setIcon(drawable);
        }else{
            getSupportActionBar().setIcon(R.drawable.ic_user_hombre);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(UpButton);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    // Sending a message to Watson Conversation Service
    private void sendMessage() {

        final String inputmessage = this.inputMessage.getText().toString().trim();
        final String[] out = new String[1];
        Message inputMessage = new Message();
        inputMessage.setMessage(inputmessage);
        inputMessage.setId("1");
        messageArrayList.add(inputMessage);
        this.inputMessage.setText("");
        mAdapter.notifyDataSetChanged();
        Thread thread = new Thread(new Runnable(){
            public void run() {
                try {
                    ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_09_20);
                    service.setUsernameAndPassword("d84c191b-c6a6-459a-9755-9d822ea47b36", "w6Vh1SY4OVx2");
                    MessageRequest newMessage = new MessageRequest.Builder().inputText(inputmessage).context(context).build();
                    MessageResponse response = service.message("d07dc9d6-ef7f-4b9b-9e85-99bdf92f8657", newMessage).execute();

                    //service.setUsernameAndPassword("f5eda796-e9ad-4589-b3a0-cadd73b732c3", "mym035f6rZlD");
                    // MessageResponse response = service.message("bf57b417-8b86-439b-b216-75cbe32bc079", newMessage).execute();
                    // MessageResponse response = service.message("bf57b417-8b86-439b-b216-75cbe32bc079", newMessage).execute();
                    if(response.getContext() !=null)
                    {
                        context.clear();
                        context = response.getContext();
                    }
                    Message outMessage = new Message();
                    if(response!=null)
                    {
                        if(response.getOutput()!=null && response.getOutput().containsKey("text"))
                        {

                            final String outputmessage = response.getOutput().get("text").toString().replace("[","").replace("]","");
                            out[0] = outputmessage;
                            outMessage.setMessage(outputmessage);
                            outMessage.setId("2");
                            messageArrayList.add(outMessage);

                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                if (mAdapter.getItemCount() > 1) {
                                    recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, mAdapter.getItemCount()-1);
                                }
                            }
                        });
                    }

                    List<com.ibm.watson.developer_cloud.conversation.v1.model.Intent> listaintents = response.getIntents();
                    for (com.ibm.watson.developer_cloud.conversation.v1.model.Intent in : listaintents ) {
                        Intenciones intenciones = new Intenciones(email,in.getIntent().toString(),getDateTime(),cleanString(inputmessage),cleanString(out[0]));
                        RegistrarIntenciones(intenciones);
                        Log.d("intent", in.getIntent().toString());//creamos un objeto Fruta y lo insertamos en la lista
                        Log.d("intent", inputmessage + " ,"+out[0]);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void RegistrarIntenciones(Intenciones intenciones){
        Call<ServerResponse> call = ApiClient.get().insertarIntencion(intenciones);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                message = response.body().getMessage();
                result = response.body().getResult();
                //Toast.makeText(WatsonActivity.this, message, Toast.LENGTH_LONG).show();

                if (result.equals(Constants.SUCCESS)){
                   //RegisterActivity.this.finish();   saca error si se finaliza
                }else{
                    //prueba de captura de error con firebase, cuando el usuario ya existe

                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                //Toast.makeText(WatsonActivity.this,"Server Error : "+ t.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(WatsonActivity.this,"Server Error Estadistics", Toast.LENGTH_LONG).show();
            }


        });
    }

    public static String cleanString(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setOnItemClickListener(new MyRecyclerViewChatAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

               if(mAdapter.getObjeto(position).getId()=="1" && checkInternetConnection()){

                   preguntanot = mAdapter.getObjeto(position).getMessage();
                   respuestaWatson = (mAdapter.getObjeto(position + 1).getMessage());
                   Intent i = new Intent(WatsonActivity.this, GuardarSolicitudActivity.class);
                   Bundle bundle = new Bundle();
                   bundle.putString("pregunta",  preguntanot);
                   bundle.putString("respuestaWatson",  respuestaWatson);
                   i.putExtras(bundle);
                   startActivity(i);
                   //Toast.makeText(WatsonActivity.this, " Clicked on  " + mAdapter.getObjeto(position).getMessage()+"respuesta"+respuestaWatson, Toast.LENGTH_LONG).show();
               }else{
                   //Toast.makeText(WatsonActivity.this, " Clicked on  " + mAdapter.getObjeto(position).getMessage(), Toast.LENGTH_LONG).show();
               }
            }
        });
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void enviarAlalytics(){
        // Send recorded usage analytics to the Mobile Analytics Service
        Analytics.send(new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                // Handle Analytics send success here.
            }

            @Override
            public void onFailure(Response response, Throwable throwable, JSONObject jsonObject) {
                // Handle Analytics send failure here.
            }
        });

        Analytics.send();
    }
    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        // Check for network connections
        if (isConnected){
            return true;
        }
        else {
            Toast.makeText(this, " No tienes conexión a Internet ", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_search:
                Toast.makeText(this, "Buscar", Toast.LENGTH_LONG ).show();
                return true;
            case R.id.action_Guardar:
                Toast.makeText(this, "Guardar", Toast.LENGTH_LONG ).show();
                return true;
            case R.id.action_Limpiar:
                Toast.makeText(this, "Limpiar", Toast.LENGTH_LONG ).show();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed(){


        Intent i = new Intent(WatsonActivity.this, FinalizarChatActivity.class);
        startActivity(i);


        /*
                            List<Message> users = messageArrayList;
                            for (Message user : users) {
                                Toast.makeText(WatsonActivity.this, " Mensaje " + user.getId() +user.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            */

        // code here to show dialog

    }
}

package com.universidadeafit.appeafit.Views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

import com.universidadeafit.appeafit.Adapters.MyRecyclerViewChatAdapter;
import com.universidadeafit.appeafit.Model.Message;
import com.universidadeafit.appeafit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.*;
import com.ibm.mobilefirstplatform.clientsdk.android.analytics.api.*;
import com.ibm.mobilefirstplatform.clientsdk.android.logger.api.*;

import org.json.JSONObject;


public class WatsonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private MyRecyclerViewChatAdapter mAdapter;
    private ArrayList messageArrayList;
    private EditText inputMessage;
    private ImageButton btnSend;
    private Map<String,Object> context = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watson);
        verToolbar("usuario",true);

        BMSClient.getInstance().initialize(getApplicationContext(), BMSClient.REGION_US_SOUTH); // Make sure that you point to your region
        // In this code example, Analytics is configured to record lifecycle events.
        Analytics.init(getApplication(), "AppEAFIT", "f364b59f-f7d2-4d30-ab6f-0053770c7720", false, Analytics.DeviceEvent.ALL);

        Analytics.enable();
        enviarAlalytics();//Envia el registro de conexion de usuarios al bot

        inputMessage = (EditText) findViewById(R.id.message);
        btnSend = (ImageButton) findViewById(R.id.btn_send);

        messageArrayList = new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewChatAdapter(messageArrayList);
        recyclerView.setAdapter(mAdapter);

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setIcon(R.drawable.ic_user_hombre);
        getSupportActionBar().setDisplayHomeAsUpEnabled(UpButton);

    }

    // Sending a message to Watson Conversation Service
    private void sendMessage() {



        final String inputmessage = this.inputMessage.getText().toString().trim();
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
                    service.setUsernameAndPassword("f02de191-0ecc-4068-9808-a2951f0da1eb", "4MXcS5njjnRt");
                    MessageRequest newMessage = new MessageRequest.Builder().inputText(inputmessage).context(context).build();
                    MessageResponse response = service.message("643f8863-1c26-4333-928c-11c129d13f0c", newMessage).execute();

                    //service.setUsernameAndPassword("f5eda796-e9ad-4589-b3a0-cadd73b732c3", "mym035f6rZlD");
                    // MessageResponse response = service.message("bf57b417-8b86-439b-b216-75cbe32bc079", newMessage).execute();
                    // MessageResponse response = service.message("bf57b417-8b86-439b-b216-75cbe32bc079", newMessage).execute();
                    if(response.getContext() !=null)
                    {
                        context.clear();
                        context = response.getContext();
                    }
                    Message outMessage=new Message();
                    if(response!=null)
                    {
                        if(response.getOutput()!=null && response.getOutput().containsKey("text"))
                        {

                            final String outputmessage = response.getOutput().get("text").toString().replace("[","").replace("]","");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setOnItemClickListener(new MyRecyclerViewChatAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

               if(mAdapter.getObjeto(position).getId()=="1"){

                   String respuestaWatson = (mAdapter.getObjeto(position + 1).getMessage());
                   Intent i = new Intent(WatsonActivity.this, GuardarSolicitudActivity.class);
                   Bundle bundle = new Bundle();
                   bundle.putString("pregunta",  mAdapter.getObjeto(position).getMessage());
                   bundle.putString("respuestaWatson",  respuestaWatson);
                   i.putExtras(bundle);
                   startActivity(i);
                   //Toast.makeText(WatsonActivity.this, " Clicked on  " + mAdapter.getObjeto(position).getMessage()+"respuesta"+respuestaWatson, Toast.LENGTH_LONG).show();
               }else{
                   Toast.makeText(WatsonActivity.this, " Clicked on  " + mAdapter.getObjeto(position).getMessage(), Toast.LENGTH_LONG).show();
               }
            }
        });
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

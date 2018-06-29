package com.example.telematica.puroapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.FloatRange;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telematica.puroapp.modelo.Adaptador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    Button btnToken, btnData;
    DatabaseReference root,primary;
    //TextView humValue,tempCValue,tempFValue;
    Double humValue,tempCValue,tempFValue;
    SwipeRefreshLayout nswipeRefreshLayout;
    Double global; // variable global para hacer giribillas
    ListView lista;


    int[] datosImg = {R.drawable.humidity,R.drawable.thermometerc,R.drawable.thermometerf};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnToken = (Button) findViewById(R.id.token);
        btnData =  (Button) findViewById(R.id.recibirData);
        /*humValue = (TextView) findViewById(R.id.humedad);
        tempCValue = (TextView) findViewById(R.id.temperaturaC);
        tempFValue = (TextView) findViewById(R.id.temperaturaF);*/
        lista = (ListView) findViewById(R.id.lista);
        nswipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);


        //database reference pointing to root of database
        root = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        primary = root.child("iHumidStore");

        btnToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tkn = null;
                tkn = FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(MainActivity.this, "Current token ["+tkn+"]",
                        Toast.LENGTH_LONG).show();
                Log.d("App", "Token ["+tkn+"]");
            }
        });


        FireIDService onRefresh = new FireIDService();
        //FireMsgService recibemMSg = new FireMsgService();
        onRefresh.onTokenRefresh();

        nswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                humValue = getFirebaseDataFromChild("humid");
                tempCValue= getFirebaseDataFromChild("tempC");
                tempFValue= getFirebaseDataFromChild("tempF");
                String [][] datos = {
                        {"Relative Humidity: ", String.valueOf(humValue)},
                        {"Centigrade Tempretarure: ", String.valueOf(tempCValue)},
                        {"Farenheit Temperature: ", String.valueOf(tempFValue)}

                };
                lista.setAdapter(null); //vacias la lista
                lista.setAdapter(new Adaptador(getApplicationContext(),datos,datosImg));
                nswipeRefreshLayout.setRefreshing(false); //para parar la animacion de refrescado


            }
        });



        String [][] datos = {

                {"Relative Humidity: ", String.valueOf(humValue)},
                {"Centigrade Tempretarure: ", String.valueOf(tempCValue)},
                {"Farenheit Temperature: ", String.valueOf(tempFValue)}

        };

        lista.setAdapter(new Adaptador(this,datos,datosImg));
    }

    /**
     *  una sola funcion para todas las peticiones
     * @param child
     * @return
     */

    private Double getFirebaseDataFromChild(final String child) {
         Double value;
        primary.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                global = dataSnapshot.getValue(Double.class);
                Toast.makeText(getApplicationContext(), child+" : "+global, Toast.LENGTH_SHORT).show();
                 }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", "onCancelled: "+databaseError.getMessage());
                global= 0.0;
            }


        });
        value= global;
        return value;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

}



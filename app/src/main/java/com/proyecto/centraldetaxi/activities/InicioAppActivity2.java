package com.proyecto.centraldetaxi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.proyecto.centraldetaxi.R;
import com.proyecto.centraldetaxi.activities.client.MapClientActivity;
import com.proyecto.centraldetaxi.activities.driver.MapDriverActivity;

public class InicioAppActivity2 extends AppCompatActivity {

    Button mButtonSoyConductor;
    Button mButtonSoyUsuario;

    SharedPreferences mPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPref.edit();




        mButtonSoyConductor = findViewById(R.id.botonSoyConductor);
        mButtonSoyUsuario = findViewById(R.id.botonSoyUsuario);

        // ------------ INICIO BOTON SOY CONDUCTOR ------------------//

        mButtonSoyConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "conductor");
                editor.apply();
                goToSelectAuth();
            }
        });

        // ------------ FIN BOTON SOY USUARIO ------------------//


        //________________________________________________________________________________//



        // ------------ INICIO BOTON SOY USUARIO ------------------//

        mButtonSoyUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "client");
                editor.apply();
                goToSelectAuth();
            }
        });

        // ------------ FIN BOTON SOY USUARIO ------------------//

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            String user = mPref.getString("user", "");
            if(user.equals("client")){
                Intent intent = new Intent(InicioAppActivity2.this, MapClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }else{
                Intent intent = new Intent(InicioAppActivity2.this, MapDriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }

        }
    }

    private void goToSelectAuth() {

        Intent intent = new Intent(InicioAppActivity2.this, LoginActivity2.class);
        startActivity(intent);

    }
}
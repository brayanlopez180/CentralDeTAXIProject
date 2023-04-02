package com.proyecto.centraldetaxi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.proyecto.centraldetaxi.R;

public class SelectOptionAuthActivity2 extends AppCompatActivity {

    Toolbar mToolbar;

    Button mButtonGoToLogin;

    TextView mTextView;

    Button mButtonGoToRegister;

    SharedPreferences mPref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_select_option_auth2);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Seleccionar opción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //BOTON GO TO LOGIN//

        //mButtonGoToLogin = findViewById(R.id.botonGoToLogin);

        //mButtonGoToRegister = findViewById(R.id.botonGoToRegister);

        mTextView = findViewById(R.id.newUser);

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mButtonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToLogin();

            }
        });

        //FIN BOTON GO TO LOGIN//

    }

    public void goToLogin(){

        Intent intent = new Intent(SelectOptionAuthActivity2.this, LoginActivity2.class);
        startActivity(intent);

    }

}